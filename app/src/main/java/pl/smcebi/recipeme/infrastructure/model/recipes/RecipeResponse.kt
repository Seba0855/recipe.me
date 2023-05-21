package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    val id: Long,
    val title: String,
    val image: String,
    val imageType: String,
    val servings: Int,
    val readyInMinutes: Int,
    val license: String? = null,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularSourceUrl: String,
    val aggregateLikes: Long,
    val healthScore: Double,
    val spoonacularScore: Double? = null,
    val pricePerServing: Double,
    val cheap: Boolean,
    val creditsText: String,
    val dairyFree: Boolean,
    val gaps: String,
    val glutenFree: Boolean,
    val instructions: String,
    val ketogenic: Boolean? = null,
    val lowFodmap: Boolean,
    val sustainable: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val whole30: Boolean? = null,
    val dishTypes: List<String>? = emptyList(),
    val extendedIngredients: List<ExtendedIngredientResponse>? = emptyList(),
    val summary: String
)
