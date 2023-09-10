package pl.smcebi.recipeme.domain.common.products

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import pl.smcebi.recipeme.datasource.products.BarcodeProductsDataSource
import pl.smcebi.recipeme.domain.common.dispatchers.DispatcherIO
import pl.smcebi.recipeme.domain.common.utils.DomainResult
import pl.smcebi.recipeme.domain.common.utils.getErrorMessage
import javax.inject.Inject

class GetProductByBarcodeUseCase @Inject internal constructor(
    private val barcodeProductsDataSource: BarcodeProductsDataSource,
    @DispatcherIO private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(ean: String): DomainResult<String, String?> = withContext(dispatcher) {
        barcodeProductsDataSource.getProduct(ean).map(
            onSuccess = { productResponse ->
                DomainResult.Success(productResponse.product.productName)
            },
            onError = { _, errorBody ->
                DomainResult.Failure(errorBody.getErrorMessage())
            }
        )
    }
}
