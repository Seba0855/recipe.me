package pl.smcebi.recipeme.ui.home.main.suggestions

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.SuggestionUI
import pl.smcebi.recipeme.ui.common.extensions.load
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.home.databinding.ItemSearchItemBinding

internal typealias OnItemClick = (Int) -> Unit

internal class SuggestionsViewHolder(
    private val binding: ItemSearchItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(suggestionUI: SuggestionUI) {
        with(binding) {
            recipeImageView.load(suggestionUI.imageUrl)
            recipeTitle.text = suggestionUI.title
        }
    }

    fun setOnSuggestionClickListener(listener: OnItemClick) {
        binding.root.setSafeOnClickListener {
            listener(bindingAdapterPosition)
        }
    }
}