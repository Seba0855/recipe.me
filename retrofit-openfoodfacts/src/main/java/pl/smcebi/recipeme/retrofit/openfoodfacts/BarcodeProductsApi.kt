package pl.smcebi.recipeme.retrofit.openfoodfacts

import pl.smcebi.recipeme.model.barcode.products.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface BarcodeProductsApi {

    @GET("product/{ean}?fields=product_name,categories,brands,_keywords,selected_images,quantity")
    suspend fun getProduct(@Path("ean") ean: String): ProductResponse
}
