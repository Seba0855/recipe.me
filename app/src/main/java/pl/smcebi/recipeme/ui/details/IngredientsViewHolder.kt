package pl.smcebi.recipeme.ui.details

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.R
import pl.smcebi.recipeme.databinding.ItemIngredientBinding
import pl.smcebi.recipeme.domain.recipes.model.IngredientUI
import pl.smcebi.recipeme.ui.common.extensions.load

internal class IngredientsViewHolder(
    private val binding: ItemIngredientBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: IngredientUI) {
        with(binding) {
            imageContainer.load(
                data = ingredient.imageUrl,
                fallback = R.drawable.ic_no_photo
            )
            ingredientNameTextView.text = ingredient.name
            amountTextView.text = buildString { append(ingredient.amount, " ", ingredient.unit) }
        }
    }
}