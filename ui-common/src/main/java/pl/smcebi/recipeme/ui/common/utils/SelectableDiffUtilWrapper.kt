package pl.smcebi.recipeme.ui.common.utils

import androidx.recyclerview.widget.DiffUtil
import pl.smcebi.recipeme.domain.common.utils.Selectable

class SelectableDiffUtilWrapper<T : Any>(
    private val wrapped: DiffUtil.ItemCallback<T>,
) : DiffUtil.ItemCallback<Selectable<T>>() {
    override fun areItemsTheSame(oldItem: Selectable<T>, newItem: Selectable<T>): Boolean =
        wrapped.areItemsTheSame(oldItem.value, newItem.value)

    override fun areContentsTheSame(oldItem: Selectable<T>, newItem: Selectable<T>): Boolean =
        if (oldItem.isSelected != newItem.isSelected) {
            false
        } else {
            wrapped.areContentsTheSame(oldItem.value, newItem.value)
        }
}