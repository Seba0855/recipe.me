// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.dagger.hilt) apply false
}

buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

allprojects {
    repositories {
        mavenCentral()  // Maven Central repository
        google()  // Google's Maven repository
    }
}

tasks.create<Delete>("clean") {
    delete(rootProject.buildDir)
}

buildScan {
    termsOfServiceUrl = "https://gradle.com/terms-of-service"
    termsOfServiceAgree = "yes"
}
