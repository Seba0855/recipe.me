package pl.smcebi.recipeme.domain.common

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import pl.smcebi.recipeme.infrastructure.remote.common.NetworkResult

fun JsonObject?.getErrorMessage(): String? =
    this?.get("message")?.jsonPrimitive?.content

fun NetworkResult<*>.getErrorMessage(): String? =
    (this as? NetworkResult.Error)?.errorBody.getErrorMessage()
