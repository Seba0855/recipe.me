package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class MeasuresResponse(
    val metric: MetricResponse,
    val us: MetricResponse
)
