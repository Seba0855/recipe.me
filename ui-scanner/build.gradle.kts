@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.parcelize)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.ui.scanner"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":barcode-scanner"))
    implementation(project(":barcode-scanner-local"))
    implementation(project(":domain-common"))
    implementation(project(":domain-recipes"))
    implementation(project(":ui-common"))

    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.recyclerview)

    // UI
    implementation(libs.google.material)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.glide)
    implementation(libs.lottie)

    // Camera & Analysis
    implementation(libs.androidx.camerax.core)
    implementation(libs.androidx.camerax.lifecycle)
    implementation(libs.androidx.camerax.view)
    implementation(libs.google.zxing)

    // Dagger
    implementation(libs.dagger.hiltLib)
    ksp(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    ksp(libs.dagger.daggerProc)

    // Utils
    implementation(libs.timber)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.testExt)
    androidTestImplementation(libs.espresso.core)
}