package pl.smcebi.recipeme.ui.home.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.smcebi.recipeme.domain.recipes.model.IngredientUI
import pl.smcebi.recipeme.ui.home.databinding.ItemIngredientBinding

internal class IngredientsAdapter : ListAdapter<IngredientUI, IngredientsViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder =
        IngredientsViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<IngredientUI>() {
            override fun areItemsTheSame(oldItem: IngredientUI, newItem: IngredientUI): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: IngredientUI, newItem: IngredientUI): Boolean =
                oldItem == newItem
        }
    }
}
