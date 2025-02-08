import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.feature)
}

android {
    namespace = "${Configuration.PACKAGE_NAME}.category"
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(projects.ui.swipeHandler)
        }

        commonMain.dependencies {
            implementation(projects.texteditor)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}