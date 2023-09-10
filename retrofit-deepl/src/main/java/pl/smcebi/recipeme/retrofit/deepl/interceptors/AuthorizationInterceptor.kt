package pl.smcebi.recipeme.retrofit.deepl.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class AuthorizationInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}
