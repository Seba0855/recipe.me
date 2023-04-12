package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponse(
    val id: Long,
    val title: String,
    val image: String,
    val imageType: String,
    val servings: Long,
    val readyInMinutes: Long,
    val license: String,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularSourceUrl: String,
    val aggregateLikes: Long,
    val healthScore: Long,
    val spoonacularScore: Long,
    val pricePerServing: Double,
    val analyzedInstructions: List<Any?>,
    val cheap: Boolean,
    val creditsText: String,
    val cuisines: List<Any?>,
    val dairyFree: Boolean,
    val diets: List<Any?>,
    val gaps: String,
    val glutenFree: Boolean,
    val instructions: String,
    val ketogenic: Boolean,
    val lowFodmap: Boolean,
    val occasions: List<Any?>,
    val sustainable: Boolean,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val whole30: Boolean,
    val weightWatcherSmartPoints: Long,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredientResponse>,
    val summary: String,
    val winePairing: WinePairingResponse
)
