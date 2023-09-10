package pl.smcebi.recipeme.datasource.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import retrofit2.HttpException
import timber.log.Timber

suspend fun <T : Any> apiCall(
    dispatcher: CoroutineDispatcher,
    json: Json,
    block: suspend () -> T
): NetworkResult<T> =
    withContext(dispatcher) {
        try {
            NetworkResult.Success(block())
        } catch (e: HttpException) {
            Timber.e(e)
            parseError(e, json)
        } catch (e: Throwable) {
            Timber.e(e)
            NetworkResult.Error(0, null)
        }
    }

private fun <T : Any> parseError(e: HttpException, json: Json): NetworkResult<T> {
    val parsedBody = try {
        e.response()?.errorBody()?.string()?.let { errorBody ->
            json.parseToJsonElement(errorBody).jsonObject
        }
    } catch (e: Throwable) {
        Timber.e(e, "Failed to parse network error")
        null
    }
    return NetworkResult.Error(e.code(), parsedBody)
}
