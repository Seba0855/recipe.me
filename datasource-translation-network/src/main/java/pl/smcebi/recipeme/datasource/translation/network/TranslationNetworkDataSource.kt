package pl.smcebi.recipeme.datasource.translation.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import pl.smcebi.recipeme.datasource.common.NetworkCoroutineDispatcher
import pl.smcebi.recipeme.datasource.common.NetworkResult
import pl.smcebi.recipeme.datasource.common.apiCall
import pl.smcebi.recipeme.datasource.translation.TranslationDataSource
import pl.smcebi.recipeme.model.deepl.TranslateBody
import pl.smcebi.recipeme.model.deepl.TranslateResponse
import pl.smcebi.recipeme.retrofit.deepl.DeepL
import pl.smcebi.recipeme.retrofit.deepl.TranslationApi
import javax.inject.Inject

internal class TranslationNetworkDataSource @Inject constructor(
    @DeepL private val api: TranslationApi,
    @NetworkCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val json: Json
) : TranslationDataSource {

    override suspend fun translate(
        text: String,
        targetLanguage: String
    ): NetworkResult<TranslateResponse> =
        apiCall(dispatcher, json) {
            api.translate(
                TranslateBody(
                    text = listOf(text), target = targetLanguage
                )
            )
        }
}
