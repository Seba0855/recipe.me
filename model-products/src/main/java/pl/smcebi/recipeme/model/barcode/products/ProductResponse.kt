package pl.smcebi.recipeme.model.barcode.products

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val code: String,
    val product: Product
)
