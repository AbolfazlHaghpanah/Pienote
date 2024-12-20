import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.haghpanah.pienote.multiplatform-application")
}

android {
    namespace = "com.haghpanah.pienote"
}

kotlin {

    android {
        compilations.all {
            kotlinOptions.jvmTarget = "21"
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.material3)
            implementation(libs.compose.material3.adaptive.navigation)
            implementation(libs.compose.ui)
            implementation(libs.compose.foundation)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.components.uiToolingPreview)
            implementation(libs.compose.animation.graphics)
            implementation(libs.compose.animation)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
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

dependencies {
    debugImplementation(compose.uiTooling)
}

compose {
    resources {
        publicResClass = true
        generateResClass = auto
    }

    desktop {
        application {
            mainClass = "com.haghpanah.pienote.MainKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "com.haghpanah.pienote"
                packageVersion = "1.0.0"
            }
        }
    }
}
