import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.compose.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pienote.subproject)
}

android {
    namespace = "${Configuration.PACKAGE_NAME}.designsystem"
}

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(libs.uri.kmp)
        implementation(libs.compose.ui.util)
        implementation(libs.androidx.lifecycle.viewmodel)
        implementation(libs.androidx.lifecycle.runtime.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.compose.material.icons)
        implementation(libs.coil.compose)
    }
}
