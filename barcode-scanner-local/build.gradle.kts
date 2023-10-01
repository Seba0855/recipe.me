@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.kapt)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.barcode.scanner.local"
}

dependencies {
    implementation(project(":barcode-scanner"))

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.google.barcodeMlKit)
    implementation(libs.androidx.camerax.core)

    implementation(libs.dagger.hiltLib)
    kapt(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    kapt(libs.dagger.daggerProc)

    implementation(libs.timber)
}
