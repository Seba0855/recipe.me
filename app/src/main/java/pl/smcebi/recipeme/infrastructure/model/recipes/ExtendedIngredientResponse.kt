package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class ExtendedIngredientResponse(
    val aisle: String,
    val amount: Double,
    val consistency: Consistency,
    val id: Long,
    val image: String?,
    val measures: MeasuresResponse,
    val meta: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val unit: String
)
