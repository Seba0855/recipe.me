package pl.smcebi.recipeme.retrofit.deepl

import pl.smcebi.recipeme.model.deepl.TranslateBody
import pl.smcebi.recipeme.model.deepl.TranslateResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TranslationApi {

    @POST("translate")
    suspend fun translate(@Body translateBody: TranslateBody): TranslateResponse
}
