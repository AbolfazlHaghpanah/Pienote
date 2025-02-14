import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.feature)
}

android {
    namespace = "${Configuration.PACKAGE_NAME}.note"
}

kotlin {
    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            implementation(projects.ui.texteditor)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}
