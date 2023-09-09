package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class QuickAnswerResponse(
    val answer: String,
    val image: String
)
