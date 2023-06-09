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
        create("androidApplication") {
            id = "pl.smcebi.recipeme.configuration"
            implementationClass = "pl.smcebi.recipeme.plugins.AndroidApplicationConventionPlugin"
        }
    }
}
