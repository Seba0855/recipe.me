package pl.smcebi.recipeme.ui.home.details

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.IngredientUI
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.home.databinding.ItemIngredientBinding
import pl.smcebi.recipeme.uicommon.R.drawable.ic_no_photo
import kotlin.math.roundToInt

internal class IngredientsViewHolder(
    private val binding: ItemIngredientBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(ingredient: IngredientUI) {
        with(binding) {
            imageContainer.load(
                data = ingredient.imageUrl,
                fallback = ic_no_photo
            )
            ingredientNameTextView.text = ingredient.name
            amountTextView.text =
                buildString { append(ingredient.amount.roundUI(), " ", ingredient.unit) }
        }
    }

    private fun Double.roundUI(): String = if (this % 1 == 0.0) {
        this.roundToInt().toString()
    } else {
        String.format("%.1f", this)
    }
}
