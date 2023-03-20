package pl.smcebi.recipeme.buildSrc

object Versions {
    const val compileSdk = 33
    const val buildTools = "30.0.3"
    const val minSdk = 26
    const val targetSdk = 33
}

object Libs {

    object Kotlin {
        const val kotlinVersion = "1.7.21"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"

        object Serialization {
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
            const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1"
            const val retrofitConverter =
                "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
        }

        object Coroutines {
            private const val version = "1.6.4"

            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object Android {
        const val gradlePlugin = "com.android.tools.build:gradle:7.3.1"
    }

    object Google {
        const val servicesGradlePlugin = "com.google.gms:google-services:4.3.14"
        const val barcodeMlKit =
            "com.google.android.gms:play-services-mlkit-barcode-scanning:18.1.0"
        const val zxing = "com.google.zxing:core:3.5.1"

        object PlayServices {
            const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4"
            const val auth = "com.google.android.gms:play-services-auth:20.3.0"
            const val phone = "com.google.android.gms:play-services-auth-api-phone:18.0.1"
            const val location = "com.google.android.gms:play-services-location:21.0.1"
        }
    }

    object Firebase {
        const val crashlyticsGradlePlugin = "com.google.firebase:firebase-crashlytics-gradle:2.9.2"
        const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx:18.3.1"
        const val analytics = "com.google.firebase:firebase-analytics-ktx:21.2.0"

        const val performance = "com.google.firebase:firebase-perf-ktx:20.1.0"
        const val performanceGradlePlugin = "com.google.firebase:perf-plugin:1.4.1"
    }

    object AndroidX {
        const val core = "androidx.core:core-ktx:1.9.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.0-rc01"
        const val annotation = "androidx.annotation:annotation:1.5.0"
        const val contraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.3.0-rc01"
        const val viewPager = "androidx.viewpager2:viewpager2:1.0.0"
        const val autofill = "androidx.autofill:autofill:1.1.0"
        const val splash = "androidx.core:core-splashscreen:1.0.0"

        object Fragment {
            private const val version = "1.5.4"

            const val lib = "androidx.fragment:fragment-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.5.1"

            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val testing = "androidx.lifecycle:lifecycle-runtime-testing:$version"
        }

        object CameraX {
            private const val version = "1.2.0-rc01"

            const val core = "androidx.camera:camera-camera2:$version"
            const val lifecycle = "androidx.camera:camera-lifecycle:$version"
            const val view = "androidx.camera:camera-view:$version"
        }

        object DataStore {
            private const val version = "1.0.0"

            const val lib = "androidx.datastore:datastore-preferences:$version"
        }
    }

    object Dagger {
        private const val version = "2.44"

        const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val hiltLib = "com.google.dagger:hilt-android:$version"
        const val hiltProc = "com.google.dagger:hilt-android-compiler:$version"
        const val daggerLib = "com.google.dagger:dagger:$version"
        const val daggerProc = "com.google.dagger:dagger-compiler:$version"
    }

    object Networking {
        private const val okHttpVersion = "4.10.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

        const val okhttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
    }

    object Material {
        const val material = "com.google.android.material:material:1.7.0"
    }

    object Navigation {
        private const val version = "2.5.3"

        const val safeArgsGradlePlugin =
            "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
        const val ui = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object JUnit {
        const val core = "junit:junit:4.13.2"
        const val android = "androidx.test.ext:junit:1.1.3"
    }

    object Test {
        const val mockk = "io.mockk:mockk:1.13.2"
        const val turbine = "app.cash.turbine:turbine:0.12.1"
    }

    object Utils {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
        const val glide = "com.github.bumptech.glide:glide:4.14.2"
        const val libphonenumber = "io.michaelrocks:libphonenumber-android:8.12.57"
        const val shimmer = "com.facebook.shimmer:shimmer:0.5.0"
    }
}