@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.secrets.plugin)
    alias(libs.plugins.kotlin.serialization)
    id("pl.smcebi.recipeme.application")
}

android {
    namespace = "pl.smcebi.recipeme"

    hilt {
        enableAggregatingTask = true
    }
}

dependencies {
    implementation(project(":retrofit-base"))
    implementation(project(":datasource-recipes"))
    implementation(project(":datasource-recipes-network"))
    implementation(project(":ui-common"))
    implementation(project(":ui-home"))

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // UI
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.glide)
    implementation(libs.glide.recyclerView) {
        // Excludes the support library because it's already included by Glide.
        isTransitive = false
    }
    implementation(libs.glide.transformation)
    implementation(libs.lottie)
    implementation(libs.androidx.emoji2)
    implementation(libs.androidx.emoji2.views)
    implementation(libs.androidx.emoji2.views.helper)

    // Networking
    implementation(libs.kotlin.serialization.serializationJson)
    implementation(libs.kotlin.serialization.retrofitConverter)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.networking.retrofit)
    implementation(libs.networking.okhttp)
    implementation(libs.networking.okhttpLogging)

    // Dagger
    implementation(libs.dagger.hiltLib)
    kapt(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    kapt(libs.dagger.daggerProc)

    // Utils
    implementation(libs.timber)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.testExt)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
