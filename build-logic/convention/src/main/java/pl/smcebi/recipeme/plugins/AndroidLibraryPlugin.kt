package pl.smcebi.recipeme.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import pl.smcebi.recipeme.utils.getVersionByName

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            // Versions are resolved from rootProject/gradle/libs.versions.toml
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(extensions.getByType<LibraryExtension>()) {
                compileSdkVersion = libs.getVersionByName("compileSdk")

                with(defaultConfig) {
                    minSdk = libs.getVersionByName("minSdk").toInt()
                    targetSdk = libs.getVersionByName("targetSdk").toInt()

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                with(buildTypes) {
                    getByName("release") {
                        minifyEnabled(false)
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro",
                        )
                    }
                }

                with(compileOptions) {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }

                with(buildFeatures) {
                    buildConfig = false
                    aidl = false
                    renderScript = false
                    shaders = false
                    resValues = false
                }
            }

            tasks.withType(KotlinCompile::class.java) {
                kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }
}
