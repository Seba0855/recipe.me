package pl.smcebi.recipeme.ui.home

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.BlurTransformation
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.ItemRecipeCardBinding
import pl.smcebi.recipeme.domain.recipes.model.RecipesUI
import pl.smcebi.recipeme.ui.common.extensions.load

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
        }
    }
}
