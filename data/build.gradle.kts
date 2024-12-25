import com.haghpanah.pienote.Configuration

plugins {
    alias(libs.plugins.pienote.android.library)
    alias(libs.plugins.pienote.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelite)
}

kotlin {
    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.sqldelite.android.driver)
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(libs.kotlin.serialization)
            implementation(libs.koin.core)
            implementation(libs.sqldelite.coroutines.extensions)
            implementation(project(":domain"))
        }
        desktopMain.dependencies {
            implementation(libs.sqldelite.driver)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "${Configuration.PACKAGE_NAME}.data"
}

sqldelight {
    databases {
        create("PienoteDatabase") {
            packageName.set("com.haghpanah.pienote.database")
        }
    }
}
