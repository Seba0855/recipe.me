package pl.smcebi.domain.products.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import pl.smcebi.domain.products.ProductUI
import pl.smcebi.domain.products.mapper.ProductToEntityMapper
import pl.smcebi.recipeme.database.dao.ProductDao
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import javax.inject.Inject

class CollectStoredProductsUseCase @Inject internal constructor(
    private val productDao: ProductDao,
    private val productToEntityMapper: ProductToEntityMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    operator fun invoke(): Flow<List<ProductUI>> =
        productDao.getProductsStream().map { productList ->
            productList.map(productToEntityMapper::mapReverse)
        }.flowOn(dispatcher)
}