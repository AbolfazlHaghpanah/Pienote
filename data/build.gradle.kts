plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelite)
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
    namespace = "com.haghpanah.pienote.data"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

sqldelight {
    databases {
        create("PienoteDatabase") {
            packageName.set("com.haghpanah.pienote.database")
        }
    }
}
