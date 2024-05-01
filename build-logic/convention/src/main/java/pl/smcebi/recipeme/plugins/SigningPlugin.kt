package pl.smcebi.recipeme.plugins

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import java.io.FileInputStream
import java.util.Properties

class SigningPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(extensions.getByType<AppExtension>()) {
                val releaseKeystorePropFile = rootProject.file("signing/release.properties")

                if (releaseKeystorePropFile.exists()) {
                    val releaseKeystoreProp = Properties()
                    releaseKeystoreProp.load(FileInputStream(releaseKeystorePropFile))

                    signingConfigs {
                        register("release") {
                            storeFile =
                                rootProject.file(releaseKeystoreProp["keystoreFile"].toString())
                            storePassword = releaseKeystoreProp["keystorePassword"].toString()
                            keyPassword = releaseKeystoreProp["keystorePassword"].toString()
                            keyAlias = releaseKeystoreProp["keystoreAlias"].toString()
                        }
                    }
                }
            }
        }
    }
}
