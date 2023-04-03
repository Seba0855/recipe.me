package pl.smcebi.recipeme.infrastructure.remote.common

import kotlinx.serialization.json.JsonObject

sealed class NetworkResult<T : Any> {
    data class Success<T : Any>(val data: T) : NetworkResult<T>()
    data class Error<T : Any>(val code: Int, val errorBody: JsonObject?) : NetworkResult<T>()

    inline fun <R> map(onSuccess: (T) -> R, onError: (Int, JsonObject?) -> R): R =
        when (this) {
            is Success -> onSuccess(data)
            is Error -> onError(code, errorBody)
        }

    val isSuccess: Boolean
        get() = this is Success
    val dataOrNull: T?
        get() = (this as? Success)?.data
    val unsafeData: T
        get() = requireNotNull(dataOrNull)
}
