package pl.smcebi.recipeme

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.AppExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = Versions.targetSdk

        defaultConfig {
            minSdk = Versions.minSdk
        }

        buildFeatures {
            buildConfig = commonExtension is AppExtension
            aidl = false
            renderScript = false
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            // Set JVM target to 1.8
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}
