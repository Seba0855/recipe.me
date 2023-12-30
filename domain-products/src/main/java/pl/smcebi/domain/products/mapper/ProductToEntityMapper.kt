package pl.smcebi.domain.products.mapper

import dagger.Reusable
import pl.smcebi.domain.products.ProductUI
import pl.smcebi.recipeme.database.entity.ProductEntity
import javax.inject.Inject

@Reusable
internal class ProductToEntityMapper @Inject internal constructor() {

    fun mapToEntity(productUI: ProductUI): ProductEntity =
        ProductEntity(
            ean = productUI.ean,
            brand = productUI.brand,
            name = productUI.name,
            imageUrl = productUI.imageUrl
        )

    fun mapReverse(productEntity: ProductEntity): ProductUI =
        ProductUI(
            ean = productEntity.ean,
            brand = productEntity.brand,
            name = productEntity.name,
            imageUrl = productEntity.imageUrl,
        )
}