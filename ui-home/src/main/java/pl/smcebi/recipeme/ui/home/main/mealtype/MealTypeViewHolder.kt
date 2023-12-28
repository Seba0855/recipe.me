package pl.smcebi.recipeme.ui.home.main.mealtype

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R.attr.colorOnSecondary
import com.google.android.material.R.attr.colorOnSecondaryContainer
import com.google.android.material.R.attr.colorSecondary
import com.google.android.material.R.attr.colorSecondaryContainer
import pl.smcebi.recipeme.domain.common.utils.Selectable
import pl.smcebi.recipeme.domain.recipes.model.MealType
import pl.smcebi.recipeme.ui.common.extensions.getStringArray
import pl.smcebi.recipeme.ui.common.extensions.getThemedColor
import pl.smcebi.recipeme.ui.common.extensions.setSafeOnClickListener
import pl.smcebi.recipeme.ui.home.R
import pl.smcebi.recipeme.ui.home.databinding.ItemMealtypeButtonBinding
import pl.smcebi.recipeme.ui.home.main.suggestions.OnItemClick

internal class MealTypeViewHolder(
    private val binding: ItemMealtypeButtonBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(mealType: Selectable<MealType>) {
        with(binding) {
            mealtypeButton.setBackgroundColor(
                getThemedColor(
                    if (mealType.isSelected) {
                        colorSecondary
                    } else {
                        colorSecondaryContainer
                    }
                )
            )
            mealtypeButton.setTextColor(
                getThemedColor(
                    if (mealType.isSelected) {
                        colorOnSecondary
                    } else {
                        colorOnSecondaryContainer
                    }
                )
            )
            mealtypeButton.text =
                getStringArray(R.array.fragment_home_mealtypes)[mealType.value.ordinal]
        }
    }

    fun setOnMealTypeClickListener(listener: OnItemClick) {
        binding.mealtypeButton.setSafeOnClickListener {
            listener(bindingAdapterPosition)
        }
    }
}