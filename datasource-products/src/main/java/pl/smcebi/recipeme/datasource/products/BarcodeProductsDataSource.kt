package pl.smcebi.recipeme.datasource.products

import pl.smcebi.recipeme.datasource.common.NetworkResult
import pl.smcebi.recipeme.model.barcode.products.ProductResponse

interface BarcodeProductsDataSource {

    suspend fun getProduct(ean: String): NetworkResult<ProductResponse>
}
