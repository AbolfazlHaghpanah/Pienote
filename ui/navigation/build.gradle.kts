import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pienote.subproject)
}

android {
    namespace = "${Configuration.PACKAGE_NAME}.ui.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.navigation.compose)
            implementation(libs.kotlin.serialization)
        }
    }
}

