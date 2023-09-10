package pl.smcebi.recipeme.datasource.translation

import pl.smcebi.recipeme.datasource.common.NetworkResult
import pl.smcebi.recipeme.model.deepl.TranslateResponse

interface TranslationDataSource {

    suspend fun translate(text: String, targetLanguage: String): NetworkResult<TranslateResponse>
}
