package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class NutritionResponse(
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
)