package pl.smcebi.recipeme.barcode.scanner.local

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
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
        .setResolutionSelector(
            ResolutionSelector.Builder()
                .setResolutionStrategy(
                    ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY
                ).build()
        )
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build()
        .apply {
            setAnalyzer(Executors.newSingleThreadExecutor(), analyzer)
        }

    override val barcodesFlow: Flow<BarcodeData> by analyzer::barcodesFlow

    override fun getCameraSelector(): CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    override fun getImageAnalysis(): ImageAnalysis = imageAnalysis
}