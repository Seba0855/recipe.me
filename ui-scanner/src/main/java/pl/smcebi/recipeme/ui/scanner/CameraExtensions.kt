package pl.smcebi.recipeme.ui.scanner

import android.content.Context
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import pl.smcebi.recipeme.ui.common.extensions.mainExecutorCompat
import timber.log.Timber

internal inline fun Context.getCameraProvider(
    crossinline block: (ProcessCameraProvider) -> Unit,
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

    cameraProviderFuture.addListener({
        block(cameraProviderFuture.get())
    }, mainExecutorCompat)
}

internal fun Camera.setAutofocusPoint() {
    val autoFocusPoint = SurfaceOrientedMeteringPointFactory(1f, 1f)
        .createPoint(0.5f, 0.5f)
    try {
        val autoFocusAction = FocusMeteringAction.Builder(
            autoFocusPoint,
            FocusMeteringAction.FLAG_AF
        ).build()
        cameraControl.startFocusAndMetering(autoFocusAction)
    } catch (e: CameraInfoUnavailableException) {
        Timber.e("Camera not accessible", e)
    }
}
