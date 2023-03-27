import pl.smcebi.recipeme.buildSrc.Libs

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlin.android) apply false
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle.plugin)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.google.servicesGradlePlugin)
        classpath(libs.firebase.crashlyticsGradlePlugin)
        classpath(libs.firebase.performanceGradlePlugin)
        classpath(libs.navigation.safeargs.plugin)
        classpath(libs.dagger.hiltGradlePlugin)
        classpath(libs.kotlin.serialization.gradlePlugin)
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
