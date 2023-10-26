package pl.smcebi.recipeme.barcode.scanner.local

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import pl.smcebi.recipeme.barcode.scanner.BarcodeData
import timber.log.Timber
import javax.inject.Inject

class BarcodeImageAnalyzer @Inject constructor(
    private val barcodeScanner: BarcodeScanner,
) : ImageAnalysis.Analyzer {

    private val sharedBarcodesFlow = MutableSharedFlow<BarcodeData>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val barcodesFlow: Flow<BarcodeData> = sharedBarcodesFlow

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        val mediaImage = image.image ?: run {
            image.close()
            return
        }
        val inputImage = InputImage.fromMediaImage(mediaImage, image.imageInfo.rotationDegrees)

        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.firstNotNullOfOrNull {
                    it.rawValue
                }
                    ?.let { data ->
                        sharedBarcodesFlow.tryEmit(BarcodeData(data))
                    }
            }
            .addOnFailureListener { exception ->
                Timber.e(exception, "Failed to recognize barcode")
            }
            .addOnCompleteListener {
                image.close()
            }
    }
}