@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.navigation.safeargs)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.domain.common"
}

dependencies {
    implementation(project(":datasource-common"))
    implementation(project(":datasource-translation"))
    implementation(project(":datasource-products"))
    implementation(project(":model-deepl"))
    implementation(project(":model-products"))

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

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
