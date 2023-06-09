package pl.smcebi.recipeme.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import pl.smcebi.recipeme.BuildConfig
import pl.smcebi.recipeme.infrastructure.remote.api.base.BaseUrlStore
import retrofit2.Retrofit
import java.time.Duration

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitBaseModule {

    @Provides
    @Reusable
    fun provideOkHttpClient(
        interceptors: Set<@JvmSuppressWildcards Interceptor>
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                interceptors.forEach {
                    addInterceptor(it)
                }
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = BODY
                        }
                    )
                }
            }
            .callTimeout(Duration.ofSeconds(CALL_TIMEOUT))
            .build()

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Reusable
    fun provideRetrofit(
        baseUrlStore: BaseUrlStore,
        client: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrlStore.baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    private const val CALL_TIMEOUT = 10L
}
