package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class ProductMatchResponse(
    val id: Long,
    val title: String,
    val description: String,
    val price: String,
    val imageUrl: String,
    val averageRating: Double,
    val ratingCount: Double,
    val score: Double,
    val link: String
)
