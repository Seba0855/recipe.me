package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class RecipeListResponse(
    val recipes: List<RecipeResponse>
)
