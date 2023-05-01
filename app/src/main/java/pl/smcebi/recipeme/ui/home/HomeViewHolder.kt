package pl.smcebi.recipeme.ui.home

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.databinding.ItemRecipeCardBinding
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI

internal class HomeViewHolder(
    private val binding: ItemRecipeCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: RecipesUI) {
        with(binding) {
            recipeTitleTextView.text = recipe.title
        }
    }
}
