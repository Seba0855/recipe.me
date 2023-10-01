package pl.smcebi.recipeme.model.deepl

import kotlinx.serialization.Serializable

@Serializable
data class TranslateResponse(
    val translations: List<Translation>
)
