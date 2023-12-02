package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class AnalyzedInstructionResponse(
    val name: String,
    val steps: List<InstructionStepResponse>
)
