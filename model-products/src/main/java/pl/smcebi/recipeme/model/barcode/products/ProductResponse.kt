package pl.smcebi.recipeme.model.barcode.products

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val code: String,
    val product: Product? = null,
    @SerialName("status_verbose") val productStatus: String? = ""
)
