package pl.smcebi.recipeme.retrofit.openfoodfacts

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.time.Duration

@Module
@InstallIn(SingletonComponent::class)
internal object BarcodeProductsModule {

    @Provides
    @Reusable
    @OpenFoodFacts
    fun provideBarcodeProductsOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .callTimeout(Duration.ofSeconds(CALL_TIMEOUT))
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Reusable
    @OpenFoodFacts
    fun provideBarcodeProductsRetrofit(
        @OpenFoodFacts client: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.OFF_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    private const val CALL_TIMEOUT = 10L

    @Provides
    @Reusable
    @OpenFoodFacts
    fun provideBarcodeProductsApi(@OpenFoodFacts retrofit: Retrofit): BarcodeProductsApi = retrofit.create()
}
