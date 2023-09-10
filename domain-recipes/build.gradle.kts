@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.domain.recipes"
}

dependencies {

    implementation(project(":datasource-common"))
    implementation(project(":datasource-recipes"))
    implementation(project(":model-recipes"))
    implementation(project(":domain-common"))

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.serializationJson)
    implementation(libs.libphonenumber)
    implementation(libs.timber)

    implementation(libs.dagger.hiltLib)
    kapt(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    kapt(libs.dagger.daggerProc)
    implementation(libs.androidx.annotation)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.testExt)
}
