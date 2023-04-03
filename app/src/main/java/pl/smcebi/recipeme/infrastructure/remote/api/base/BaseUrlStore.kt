package pl.smcebi.recipeme.infrastructure.remote.api.base

import android.content.Intent
import pl.smcebi.recipeme.BuildConfig
import javax.inject.Inject

class BaseUrlStore @Inject constructor() {
    var baseUrl: String = BuildConfig.BASE_URL

    fun parseIntent(intent: Intent?): Boolean {
        intent?.data?.getQueryParameter(BASE_URL_KEY)?.let { baseUrl ->
            this.baseUrl = baseUrl
            return true
        }
        return false
    }

    companion object {
        const val BASE_URL_KEY = "baseUrl"
    }
}
