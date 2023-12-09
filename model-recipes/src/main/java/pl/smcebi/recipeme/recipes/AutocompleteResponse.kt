package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteResponse(
    val id: Long,
    val title: String,
    val imageType: String,
)
