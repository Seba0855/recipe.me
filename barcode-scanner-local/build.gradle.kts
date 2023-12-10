@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
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
    ksp(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    ksp(libs.dagger.daggerProc)

    implementation(libs.timber)
}
