package pl.smcebi.recipeme.ui.common.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

val Context.mainExecutorCompat: Executor
    get() = ContextCompat.getMainExecutor(this)

fun Context.getColorCompat(@ColorRes res: Int) = ContextCompat.getColor(this, res)

fun Context.getDrawableCompat(@DrawableRes res: Int) = ContextCompat.getDrawable(this, res)

fun Context.checkPermissionCompat(permission: String): Boolean =
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
