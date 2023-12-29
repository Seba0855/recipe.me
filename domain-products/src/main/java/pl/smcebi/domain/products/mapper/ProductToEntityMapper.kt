package pl.smcebi.domain.products.mapper

import dagger.Reusable
import pl.smcebi.domain.products.ProductUI
import pl.smcebi.recipeme.database.entity.ProductEntity
import javax.inject.Inject

@Reusable
internal class ProductToEntityMapper @Inject internal constructor() {

    fun mapToEntity(productUI: ProductUI): ProductEntity =
        ProductEntity(
            brand = productUI.brand,
            name = productUI.name,
            imageUrl = productUI.imageUrl
        )

    fun mapReverse(recipeEntity: ProductEntity): ProductUI =
        ProductUI(
            brand = recipeEntity.brand,
            name = recipeEntity.name,
            imageUrl = recipeEntity.imageUrl
        )
}