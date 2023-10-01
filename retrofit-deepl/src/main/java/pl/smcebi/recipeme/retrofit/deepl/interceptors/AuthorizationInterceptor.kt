package pl.smcebi.recipeme.retrofit.deepl.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import pl.smcebi.recipeme.retrofit.deepl.BuildConfig
import javax.inject.Inject

internal class AuthorizationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader(AUTHORIZATION_HEADER, BuildConfig.DEEPL_API_KEY)
            .build()
        return chain.proceed(newRequest)
    }

    private companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}
