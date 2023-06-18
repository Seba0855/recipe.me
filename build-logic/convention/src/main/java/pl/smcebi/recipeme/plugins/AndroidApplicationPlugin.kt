package pl.smcebi.recipeme.plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import pl.smcebi.recipeme.Versions
import pl.smcebi.recipeme.configureKotlinAndroid

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.configure<AppExtension> {
                configureKotlinAndroid(this as ApplicationExtension)
                compileSdkVersion = libs.findVersion("compileSdk").get().requiredVersion

                with(defaultConfig) {
                    applicationId = "pl.smcebi.recipeme"
                    minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                    targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
                    versionCode = System.getenv("VERSION_CODE")?.toInt() ?: 1
                    versionName = libs.findVersion("versionName").get().requiredVersion

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
            }
        }
    }
}