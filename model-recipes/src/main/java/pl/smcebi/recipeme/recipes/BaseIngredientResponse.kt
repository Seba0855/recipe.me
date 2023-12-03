package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class BaseIngredientResponse(
    val id: Long,
    val name: String,
    val localizedName: String,
    val image: String,
)
