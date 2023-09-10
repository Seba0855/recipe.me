package pl.smcebi.recipeme.model.deepl

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslateBody(
    val text: List<String>,
    @SerialName("target_lang") val target: String,
)
