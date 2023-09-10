package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class QuickAnswerResponse(
    val answer: String,
    val image: String
)
