package pl.smcebi.recipeme.ui.home.main.mealtype

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.MealType
import pl.smcebi.recipeme.ui.common.extensions.getStringArray
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.ItemMealtypeButtonBinding
import pl.smcebi.recipeme.ui.home.main.suggestions.OnItemClick

internal class MealtypeViewHolder(
    private val binding: ItemMealtypeButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mealtype: MealType) {
        with(binding) {
            mealtypeButton.text = getStringArray(R.array.fragment_home_mealtypes)[mealtype.ordinal]
        }
    }

    fun setOnMealtypeClickListener(listener: OnItemClick) {
        binding.root.setSafeOnClickListener {
            listener(bindingAdapterPosition)
        }
    }
}