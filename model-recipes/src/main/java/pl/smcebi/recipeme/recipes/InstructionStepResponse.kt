package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class InstructionStepResponse(
    val number: Int,
    val step: String,
    val ingredients: List<BaseIngredientResponse>
)
