pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.12"
}

rootProject.name = "recipe.me"
include(":app")
include(":datasource-recipes")
include(":ui-common")
include(":domain-common")
include(":retrofit-base")
include(":datasource-recipes-network")
include(":model-recipes")
include(":retrofit-recipes")
include(":domain-recipes")
include(":datasource-common")
include(":ui-home")
include(":retrofit-deepl")
include(":retrofit-openfoodfacts")
include(":model-deepl")
