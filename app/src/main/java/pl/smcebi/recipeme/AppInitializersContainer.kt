package pl.smcebi.recipeme

import android.app.Application
import javax.inject.Inject

class AppInitializersContainer @Inject constructor(
    private vararg val appInitializers: AppInitializer
) {

    fun init(app: Application) {
        appInitializers.forEach { it.init(app) }
    }
}