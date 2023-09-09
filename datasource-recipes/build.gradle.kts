@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("pl.smcebi.recipeme.library")
}

android {
    namespace = "pl.smcebi.recipeme.datasource.recipes"
}

dependencies {
    implementation(project(":datasource-common"))
    implementation(project(":model-recipes"))
}
