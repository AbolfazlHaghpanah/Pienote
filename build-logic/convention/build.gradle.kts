import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.haghpanah.pienote.buildlogic"

dependencies {
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.andorid.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

gradlePlugin {
    plugins {
        register("MultiplatformApplicationConversionPlugin") {
            id = libs.plugins.pienote.application.multiplatform.get().pluginId
            implementationClass = "MultiplatformApplicationConversionPlugin"
        }
    }

    plugins {
        register("AndroidLibraryConversionPlugin") {
            id = libs.plugins.pienote.android.library.get().pluginId
            implementationClass = "AndroidLibraryConversionPlugin"
        }
    }

    plugins {
        register("MultiplatformConversionPlugin") {
            id = libs.plugins.pienote.multiplatform.get().pluginId
            implementationClass = "MultiplatformConversionPlugin"
        }
    }

    plugins {
        register("ComposeMultiplatformConversionPlugin") {
            id = libs.plugins.pienote.compose.multiplatform.get().pluginId
            implementationClass = "ComposeMultiplatformConversionPlugin"
        }
    }
}