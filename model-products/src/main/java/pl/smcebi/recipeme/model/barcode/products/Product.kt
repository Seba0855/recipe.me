package pl.smcebi.recipeme.model.barcode.products

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("_keywords") val keywords: List<String> = emptyList(),
    @SerialName("product_name") val productName: String = "",
    @SerialName("selected_images") val selectedImages: ProductImage? = null,
    val brands: String = "",
    val categories: String = "",
    val quantity: String = "",
)
