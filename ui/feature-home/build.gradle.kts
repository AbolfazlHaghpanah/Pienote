import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.feature)
}

android {
    namespace = "${Configuration.PACKAGE_NAME}.home"
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(projects.ui.swipeHandler)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(projects.ui.texteditor)
        }
    }
}
