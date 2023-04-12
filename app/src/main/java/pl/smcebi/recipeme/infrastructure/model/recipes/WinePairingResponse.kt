package pl.smcebi.recipeme.infrastructure.model.recipes

import kotlinx.serialization.Serializable

@Serializable
data class WinePairingResponse(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatchResponse>
)
