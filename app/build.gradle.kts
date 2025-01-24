import com.haghpanah.pienote.Configuration
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.pienote.application.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pienote.subproject)
}

android {
    namespace = Configuration.PACKAGE_NAME
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.kotlin.serialization)
            implementation(libs.koin.core)
            implementation(libs.koin.viewmodel)
            implementation(libs.koin.viewmodel.navigation)
            implementation(project(":ui"))
            implementation(project(":data"))
            implementation(project(":domain"))
            implementation(project(":texteditor"))
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose {
    resources {
        publicResClass = true
        generateResClass = auto
    }

    desktop {
        application {
            mainClass = "${Configuration.PACKAGE_NAME}.MainKt"
            nativeDistributions {
                packageName = Configuration.PACKAGE_NAME
                packageVersion = Configuration.VERSION_NAME
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

                macOS {
                    iconFile.set(
                        File(
                            project.rootDir,
                            "ui/src/commonMain/composeResources/drawable/pienote_icon_mac_os.icns"
                        )
                    )
                }
            }
        }
    }
}
