package pl.smcebi.recipeme.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
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
            }

            tasks.withType<KotlinCompile> {
                compilerOptions.freeCompilerArgs.addAll("-Xcontext-receivers")
            }

            with(kotlinExtension) {
                jvmToolchain(17)
            }
        }
    }
}
