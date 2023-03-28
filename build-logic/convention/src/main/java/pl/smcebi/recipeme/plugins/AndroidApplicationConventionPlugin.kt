package pl.smcebi.recipeme.plugins

import com.android.build.api.dsl.ApplicationExtension
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

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = Versions.targetSdk
                configureKotlinAndroid(this)
            }
        }
    }
}