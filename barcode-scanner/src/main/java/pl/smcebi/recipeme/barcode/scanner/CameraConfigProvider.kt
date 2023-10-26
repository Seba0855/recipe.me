package pl.smcebi.recipeme.barcode.scanner

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis

interface CameraConfigProvider {
    fun getCameraSelector(): CameraSelector
    fun getImageAnalysis(): ImageAnalysis
}