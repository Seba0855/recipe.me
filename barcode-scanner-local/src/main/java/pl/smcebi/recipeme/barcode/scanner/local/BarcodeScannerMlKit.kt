package pl.smcebi.recipeme.barcode.scanner.local

import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import kotlinx.coroutines.flow.Flow
import pl.smcebi.recipeme.barcode.scanner.BarcodeData
import pl.smcebi.recipeme.barcode.scanner.InternalBarcodeScanner
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarcodeScannerMlKit @Inject constructor(
    private val analyzer: BarcodeImageAnalyzer
) : InternalBarcodeScanner {

    private val imageAnalysis = ImageAnalysis.Builder()
        .setTargetResolution(Size(IMAGE_WIDTH, IMAGE_HEIGHT))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .apply {
            setAnalyzer(Executors.newSingleThreadExecutor(), analyzer)
        }

    override val barcodesFlow: Flow<BarcodeData> by analyzer::barcodesFlow

    override fun getCameraSelector(): CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun getImageAnalysis(): ImageAnalysis = imageAnalysis

    private companion object {
        private const val IMAGE_WIDTH = 1280
        private const val IMAGE_HEIGHT = 720
    }
}