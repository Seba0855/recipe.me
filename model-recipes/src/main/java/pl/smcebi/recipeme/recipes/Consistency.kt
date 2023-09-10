package pl.smcebi.recipeme.recipes

import kotlinx.serialization.Serializable

@Serializable
enum class Consistency(val value: String) {
    LIQUID("liquid"),
    SOLID("solid");

    companion object {
        fun fromValue(value: String): Consistency = when (value) {
            "liquid" -> LIQUID
            "solid" -> SOLID
            else -> throw IllegalArgumentException()
        }
    }
}
