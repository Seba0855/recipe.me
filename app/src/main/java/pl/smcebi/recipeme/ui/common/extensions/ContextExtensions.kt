package pl.smcebi.recipeme.ui.common.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import pl.smcebi.recipeme.R

@ColorInt
fun Context.getThemedColor(@AttrRes res: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(res, typedValue, true)
    return typedValue.data
}

fun Context.getAppPackageUri(): Uri = Uri.parse("package:$packageName")

fun Context.toast(
    text: String,
    length: Int = Toast.LENGTH_LONG,
) {
    Toast.makeText(this, text, length).show()
}

fun Context.copyToClipboard(
    label: String,
    text: String,
) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
        toast(text = getString(R.string.common_copy_confirmation))
    }
}
