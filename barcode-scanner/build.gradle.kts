@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.barcode.scanner"
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.androidx.camerax.core)
}
