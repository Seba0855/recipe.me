package pl.smcebi.recipeme.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.ItemRecipeCardBinding
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener

internal typealias OnItemViewClick = (View, Int) -> Unit
internal typealias OnItemClick = (Int) -> Unit

internal class HomeViewHolder(
    private val binding: ItemRecipeCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(recipe: RecipesUI) {
        with(binding) {
            recipeImage.load(
                data = recipe.imageUrl,
                fallback = R.drawable.recipe_placeholder
            )
            recipeTitleTextView.text = recipe.title
            durationTextView.text = recipe.durationAndServings
            root.transitionName = recipe.imageUrl
        }
    }

    fun setOnRecipeClickListener(listener: OnItemViewClick) {
        binding.root.setSafeOnClickListener {
            listener(binding.root, adapterPosition)
        }
    }

    fun setOnBookmarkClickListener(listener: OnItemClick) {
        binding.bookmarkButton.setSafeOnClickListener {
            listener(adapterPosition)
        }
    }
}
