@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.kapt)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.datasource.recipes.network"
}

dependencies {
    implementation(project(":retrofit-recipes"))
    implementation(project(":datasource-common"))
    implementation(project(":datasource-recipes"))
    implementation(project(":model-recipes"))

    implementation(libs.kotlin.serialization.serializationJson)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.networking.okhttp)
    implementation(libs.networking.retrofit)

    implementation(libs.dagger.hiltLib)
    kapt(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    kapt(libs.dagger.daggerProc)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.testExt)
    androidTestImplementation(libs.espresso.core)
}
