package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class NutritionResponse(
    val calories: String,
    val carbs: String,
    val fat: String,
    val protein: String,
)
