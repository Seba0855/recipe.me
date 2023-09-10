package pl.smcebi.recipeme.model.deepl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Translation(
    @SerialName("detected_source_language") val sourceLanguage: String,
    val text: String,
)
