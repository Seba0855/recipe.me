package pl.smcebi.recipeme.ui.common.extensions

import androidx.annotation.ArrayRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.ViewHolder.getString(@StringRes resourceId: Int) =
    itemView.context.getString(resourceId)

fun RecyclerView.ViewHolder.getStringArray(@ArrayRes resourceId: Int) =
    itemView.context.resources.getStringArray(resourceId)