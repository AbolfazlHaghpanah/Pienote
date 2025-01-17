plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pienote.subproject)
}

android {
    namespace = "com.haghpanah.pienote.ui"

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

kotlin {
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(libs.uri.kmp)
            implementation(libs.compose.ui.util)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.compose.material.icons)
            implementation(libs.kotlin.serialization)
            implementation(libs.kotlin.reflect)
            implementation(libs.coil.compose)
            implementation(libs.koin.core)
            implementation(libs.koin.viewmodel)
            implementation(libs.koin.viewmodel.navigation)
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
    implementation(libs.androidx.navigation.runtime.ktx)
    debugImplementation(compose.uiTooling)
}
