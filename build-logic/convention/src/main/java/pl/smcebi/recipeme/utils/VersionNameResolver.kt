package pl.smcebi.recipeme.utils

import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.generateAppVersionCode(): Int {
    val versionMajor = getVersionByName("versionMajor").toInt()
    val versionMinor = getVersionByName("versionMinor").toInt()
    val versionPatch = getVersionByName("versionPatch").toInt()

    return 1_000_000 * versionMajor + 10_000 * versionMinor + 100 * versionPatch
}

internal fun VersionCatalog.generateAppVersionName(): String {
    val versionMajor = getVersionByName("versionMajor").toInt()
    val versionMinor = getVersionByName("versionMinor").toInt()
    val versionPatch = getVersionByName("versionPatch").toInt()

    return "$versionMajor.$versionMinor.$versionPatch"
}

internal fun VersionCatalog.getVersionByName(name: String): String =
    findVersion(name).get().requiredVersion
