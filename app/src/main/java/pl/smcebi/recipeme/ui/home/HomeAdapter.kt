package pl.smcebi.recipeme.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.smcebi.recipeme.databinding.ItemRecipeCardBinding
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal class HomeAdapter : ListAdapter<RecipesUI, HomeViewHolder>(RecipeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            ItemRecipeCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    internal class RecipeDiffUtil : DiffUtil.ItemCallback<RecipesUI>() {
        override fun areItemsTheSame(oldItem: RecipesUI, newItem: RecipesUI): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: RecipesUI, newItem: RecipesUI): Boolean =
            oldItem == newItem
    }
}
