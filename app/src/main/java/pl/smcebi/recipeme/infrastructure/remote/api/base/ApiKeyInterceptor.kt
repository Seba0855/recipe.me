package pl.smcebi.recipeme.infrastructure.remote.api.base

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import pl.smcebi.recipeme.BuildConfig

internal class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val currentUrl = chain.request().url
        val newUrl =
            currentUrl.newBuilder().addQueryParameter(API_QUERY_PARAMETER, BuildConfig.API_KEY)
                .build()
        val currentRequest = chain.request().newBuilder()
        val newRequest = currentRequest.url(newUrl).build()
        return chain.proceed(newRequest)
    }

    private companion object {
        const val API_QUERY_PARAMETER = "apiKey"
    }
}
