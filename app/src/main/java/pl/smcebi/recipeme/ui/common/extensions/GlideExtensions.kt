package pl.smcebi.recipeme.ui.common.extensions

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

fun ImageView.load(
    data: Any?,
    @DrawableRes placeholder: Int = 0,
    @DrawableRes fallback: Int = 0,
    @DrawableRes error: Int = fallback,
    useOriginalSize: Boolean = false,
) {
    Glide.with(this)
        .load(data)
        .placeholder(placeholder)
        .fallback(fallback)
        .error(error)
        .apply {
            if (useOriginalSize) {
                override(SIZE_ORIGINAL, SIZE_ORIGINAL)
            }
        }
        .into(this)
}
