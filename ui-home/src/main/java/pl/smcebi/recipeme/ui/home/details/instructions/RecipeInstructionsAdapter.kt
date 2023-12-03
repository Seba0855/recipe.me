package pl.smcebi.recipeme.ui.home.details.instructions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.smcebi.recipeme.domain.recipes.model.InstructionUI
import pl.smcebi.recipeme.ui.home.databinding.ItemInstructionStepBinding

internal class RecipeInstructionsAdapter :
    ListAdapter<InstructionUI, RecipeInstructionsViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeInstructionsViewHolder =
        RecipeInstructionsViewHolder(
            ItemInstructionStepBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecipeInstructionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<InstructionUI>() {
            override fun areItemsTheSame(oldItem: InstructionUI, newItem: InstructionUI): Boolean =
                oldItem.number == newItem.number

            override fun areContentsTheSame(
                oldItem: InstructionUI,
                newItem: InstructionUI
            ): Boolean =
                oldItem == newItem
        }
    }
}
