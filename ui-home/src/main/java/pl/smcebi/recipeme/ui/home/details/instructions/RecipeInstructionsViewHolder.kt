package pl.smcebi.recipeme.ui.home.details.instructions

import androidx.recyclerview.widget.RecyclerView
import pl.smcebi.recipeme.domain.recipes.model.InstructionUI
import pl.smcebi.recipeme.ui.home.databinding.ItemInstructionStepBinding

internal class RecipeInstructionsViewHolder(
    private val binding: ItemInstructionStepBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(instructionUI: InstructionUI) {
        with(binding) {
            stepIndicatorTextView.text = instructionUI.number
            stepInstructionTextView.text = instructionUI.instruction
        }
    }
}
