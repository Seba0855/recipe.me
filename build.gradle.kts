import pl.smcebi.recipeme.buildSrc.Libs

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.4.2" apply false
    id("com.android.library") version "7.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.plugin)
//        classpath(Libs.Kotlin.gradlePlugin)
//        classpath(Libs.Android.gradlePlugin)
//        classpath(Libs.Google.servicesGradlePlugin)
//        classpath(Libs.Firebase.crashlyticsGradlePlugin)
//        classpath(Libs.Firebase.performanceGradlePlugin)
//        classpath(Libs.Navigation.safeArgsGradlePlugin)
//        classpath(Libs.Dagger.hiltGradlePlugin)
//        classpath(Libs.Kotlin.Serialization.gradlePlugin)
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
