package pl.smcebi.recipeme.ui.scanner

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.impl.CameraConfigProvider
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ScannerViewModel @Inject constructor(
    private val imageAnalysis: ImageAnalysis,
    private val cameraConfigProvider: CameraConfigProvider,
) : ViewModel() {

}
