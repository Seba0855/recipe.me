package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class MeasuresResponse(
    val metric: MetricResponse,
    val us: MetricResponse
)
