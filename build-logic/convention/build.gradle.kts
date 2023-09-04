plugins {
    `kotlin-dsl`
}

group = "pl.smcebi.recipeme.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("application") {
            id = "pl.smcebi.recipeme.application"
            implementationClass = "pl.smcebi.recipeme.plugins.AndroidApplicationPlugin"
        }
        create("library") {
            id = "pl.smcebi.recipeme.library"
            implementationClass = "pl.smcebi.recipeme.plugins.AndroidLibraryPlugin"
        }
        create("model") {
            id = "pl.smcebi.recipeme.model"
            implementationClass = "pl.smcebi.recipeme.plugins.KotlinLibraryPlugin"
        }
    }
}
