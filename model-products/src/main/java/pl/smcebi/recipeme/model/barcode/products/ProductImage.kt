package pl.smcebi.recipeme.model.barcode.products

import kotlinx.serialization.Serializable

@Serializable
data class ProductImage(
    val front: FrontImage
) {
    @Serializable
    data class FrontImage(
        val display: Display
    ) {
        @Serializable
        data class Display(
            val pl: String? = null,
        )
    }
}
