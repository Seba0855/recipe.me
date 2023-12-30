package pl.smcebi.domain.products.store

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.domain.products.ProductUI
import pl.smcebi.domain.products.mapper.ProductToEntityMapper
import pl.smcebi.recipeme.database.dao.ProductDao
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import javax.inject.Inject

class SaveProductUseCase @Inject internal constructor(
    private val productDao: ProductDao,
    private val productToEntityMapper: ProductToEntityMapper,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(productUI: ProductUI): DomainResult<Unit, Unit> =
        withContext(dispatcher) {
            productDao.insertProduct(productToEntityMapper.mapToEntity(productUI))
            DomainResult.success()
        }
}