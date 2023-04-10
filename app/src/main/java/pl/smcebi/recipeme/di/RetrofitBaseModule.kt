package pl.smcebi.recipeme.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.Duration
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.smcebi.recipeme.BuildConfig
import pl.smcebi.recipeme.infrastructure.remote.api.base.BaseUrlStore
import retrofit2.Retrofit

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
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .callTimeout(Duration.ofSeconds(CALL_TIMEOUT))
            .build()

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
