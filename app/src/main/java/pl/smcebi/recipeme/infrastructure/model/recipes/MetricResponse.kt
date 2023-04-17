package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class MetricResponse(
    val amount: Double,
    val unitLong: String,
    val unitShort: String
)
