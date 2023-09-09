package pl.smcebi.recipeme.retrofit.base

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseUrlStore @Inject constructor() {
    var baseUrl: String = BuildConfig.BUILD_TYPE

    companion object {
        const val BASE_URL_KEY = "baseUrl"
    }
}
