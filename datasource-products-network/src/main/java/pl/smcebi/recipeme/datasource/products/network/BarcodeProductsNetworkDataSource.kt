package pl.smcebi.recipeme.datasource.products.network

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import pl.smcebi.recipeme.datasource.common.NetworkCoroutineDispatcher
import pl.smcebi.recipeme.datasource.common.NetworkResult
import pl.smcebi.recipeme.datasource.common.apiCall
import pl.smcebi.recipeme.datasource.products.BarcodeProductsDataSource
import pl.smcebi.recipeme.model.barcode.products.ProductResponse
import pl.smcebi.recipeme.retrofit.openfoodfacts.BarcodeProductsApi
import pl.smcebi.recipeme.retrofit.openfoodfacts.OpenFoodFacts
import javax.inject.Inject

internal class BarcodeProductsNetworkDataSource @Inject constructor(
    @OpenFoodFacts private val api: BarcodeProductsApi,
    @NetworkCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val json: Json
): BarcodeProductsDataSource {

    override suspend fun getProduct(ean: String): NetworkResult<ProductResponse> =
        apiCall(dispatcher, json) {
            api.getProduct(ean)
        }
}
