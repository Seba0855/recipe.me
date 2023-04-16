package pl.smcebi.recipeme

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var initializersContainer: AppInitializersContainer

    override fun onCreate() {
        super.onCreate()
        initializersContainer.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
