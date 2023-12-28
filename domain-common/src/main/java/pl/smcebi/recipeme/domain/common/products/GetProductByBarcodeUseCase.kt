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
    suspend operator fun invoke(ean: String): DomainResult<ProductUI, String?> =
        withContext(dispatcher) {
            barcodeProductsDataSource.getProduct(ean).map(
                onSuccess = { productResponse ->
                    val product = productResponse.product

                    if (product == null) {
//                    DomainResult.Success(productResponse.productStatus.toString())
                        DomainResult.Failure("Product not found")
                    } else {
                        DomainResult.Success(
                            ProductUI(
                                brand = product.brands,
                                name = product.productName,
                                imageUrl = product.selectedImages?.front?.display?.pl.orEmpty()
                            )
                        )
                    }
                },
                onError = { _, errorBody ->
                    DomainResult.Failure(errorBody.getErrorMessage())
                }
            )
        }
}
