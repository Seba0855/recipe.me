package pl.smcebi.recipeme.plugins

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import pl.smcebi.recipeme.Versions
import pl.smcebi.recipeme.androidTestImplementation
import pl.smcebi.recipeme.configureKotlinAndroid
import pl.smcebi.recipeme.testImplementation

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = Versions.targetSdk
                configureKotlinAndroid(this)

                dependencies {
                    androidTestImplementation(kotlin("test"))
                    testImplementation(kotlin("test"))
                }
            }
        }
    }
}