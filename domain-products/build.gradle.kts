@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.domain.products"
}

dependencies {

    implementation(project(":datasource-common"))
    implementation(project(":datasource-products"))
    implementation(project(":domain-common"))
    implementation(project(":model-products"))
    implementation(project(":database"))

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.serializationJson)
    implementation(libs.timber)

    implementation(libs.dagger.hiltLib)
    ksp(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    ksp(libs.dagger.daggerProc)
    implementation(libs.androidx.annotation)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.testExt)
}