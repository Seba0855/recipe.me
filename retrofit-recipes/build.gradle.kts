@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.retrofit.recipes"
}

dependencies {
    implementation(project(":retrofit-base"))
    implementation(project(":model-recipes"))

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)

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
}
