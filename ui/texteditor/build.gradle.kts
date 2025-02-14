import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pienote.subproject)
}
android {
    namespace = "${Configuration.PACKAGE_NAME}.ui.texteditor"

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
            implementation(libs.compose.material.icons)
            implementation(libs.kotlin.serialization)
            implementation(libs.coil.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}
