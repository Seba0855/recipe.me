package pl.smcebi.recipeme.retrofit.deepl

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
import pl.smcebi.recipeme.retrofit.deepl.interceptors.AuthorizationInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.time.Duration

@Module
@InstallIn(SingletonComponent::class)
internal object TranslationModule {

    @Provides
    @Reusable
    @DeepL
    fun provideTranslationOkHttpClient(
        authorizationInterceptor: AuthorizationInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                addInterceptor(authorizationInterceptor)
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
    @DeepL
    fun provideTranslationsRetrofit(
        @DeepL client: OkHttpClient,
        json: Json
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.DEEPL_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    private const val CALL_TIMEOUT = 10L
    @Provides
    @Reusable
    @DeepL
    fun provideTranslationApi(@DeepL retrofit: Retrofit): TranslationApi = retrofit.create()
}
