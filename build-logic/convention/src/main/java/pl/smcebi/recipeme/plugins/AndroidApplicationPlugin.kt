package pl.smcebi.recipeme.plugins

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
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
            }

            // Versions are resolved from rootProject/gradle/libs.versions.toml
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(extensions.getByType<AppExtension>()) {
                compileSdkVersion = libs.getVersionByName("compileSdk")
                buildToolsVersion = libs.getVersionByName("buildTools")

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
                    }
                    getByName("release") {
                        isDebuggable = false
                        isMinifyEnabled = true
                        isShrinkResources = false

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
