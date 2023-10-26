package pl.smcebi.recipeme.ui.common.vibration

import android.os.VibrationEffect
import android.os.Vibrator
import javax.inject.Inject

class VibrationProvider @Inject internal constructor(
    private val vibrator: Vibrator
) {

    fun vibrate(duration: Long, amplitude: Int) {
        vibrator.vibrate(VibrationEffect.createOneShot(duration, amplitude))
    }
}