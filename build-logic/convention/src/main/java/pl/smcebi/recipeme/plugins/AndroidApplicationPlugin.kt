package pl.smcebi.recipeme.plugins

import com.android.build.api.dsl.ApkSigningConfig
import com.android.build.api.dsl.SigningConfig
import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.de.undercouch.gradle.tasks.download.org.apache.commons.logging.LogFactory.release
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import pl.smcebi.recipeme.utils.generateAppVersionCode
import pl.smcebi.recipeme.utils.generateAppVersionName
import pl.smcebi.recipeme.utils.getVersionByName

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("androidx.navigation.safeargs.kotlin")
                apply("pl.smcebi.recipeme.signing")
            }

            // Versions are resolved from rootProject/gradle/libs.versions.toml
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(extensions.getByType<AppExtension>()) {
                compileSdkVersion = libs.getVersionByName("compileSdk")

                with(defaultConfig) {
                    applicationId = "pl.smcebi.recipeme"
                    minSdk = libs.getVersionByName("minSdk").toInt()
                    targetSdk = libs.getVersionByName("targetSdk").toInt()
                    versionCode = libs.generateAppVersionCode()
                    versionName = libs.generateAppVersionName()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                with(buildTypes) {
                    getByName("debug") {
                        isDebuggable = true
                        applicationIdSuffix = ".debug"
                        versionNameSuffix = "-DEBUG"
                        matchingFallbacks += "release"
                        signingConfig = signingConfigs.getByName("debug")
                    }
                    getByName("release") {
                        isDebuggable = false
                        isMinifyEnabled = true
                        isShrinkResources = false
                        signingConfig = signingConfigs.findByName("release") ?: signingConfigs.getByName("debug")

                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                with(buildFeatures) {
                    buildConfig = true
                    viewBinding = true
                }

                with(kotlinExtension) {
                    jvmToolchain(17)
                }
            }
        }
    }
}
