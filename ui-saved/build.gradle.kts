@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.navigation.safeargs)
    alias(libs.plugins.kotlin.parcelize)
    id("pl.smcebi.recipeme.library")
}
android {
    namespace = "pl.smcebi.recipeme.ui.saved"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(":domain-common"))
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

    // Dagger
    implementation(libs.dagger.hiltLib)
    kapt(libs.dagger.hiltProc)
    implementation(libs.dagger.daggerLib)
    kapt(libs.dagger.daggerProc)

    // Utils
    implementation(libs.timber)

    testImplementation(libs.junit.core)
    androidTestImplementation(libs.junit.testExt)
    androidTestImplementation(libs.espresso.core)
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}
