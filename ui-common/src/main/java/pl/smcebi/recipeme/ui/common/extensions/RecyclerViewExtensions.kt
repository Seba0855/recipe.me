package pl.smcebi.recipeme.ui.common.extensions

import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.ViewHolder.getString(@StringRes resourceId: Int) =
    itemView.context.getString(resourceId)

fun RecyclerView.ViewHolder.getStringArray(@ArrayRes resourceId: Int): Array<String> =
    itemView.context.resources.getStringArray(resourceId)

@ColorInt
fun RecyclerView.ViewHolder.getColorCompat(@ColorRes res: Int): Int =
    ContextCompat.getColor(itemView.context, res)

@ColorInt
fun RecyclerView.ViewHolder.getThemedColor(@AttrRes res: Int): Int {
    val typedValue = TypedValue()
    itemView.context.theme.resolveAttribute(res, typedValue, true)
    return typedValue.data
}