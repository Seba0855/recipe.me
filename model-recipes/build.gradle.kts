@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("pl.smcebi.recipeme.model")
}

dependencies {
    implementation(libs.kotlin.serialization.serializationJson)
}
