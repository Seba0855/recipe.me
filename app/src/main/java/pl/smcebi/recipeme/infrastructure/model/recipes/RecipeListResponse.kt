package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class RecipeListResponse(
    val recipes: List<RecipeResponse>
)
