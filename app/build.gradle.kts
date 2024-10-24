import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}

detekt {
    source.setFrom("src/main/java", "src/main/kotlin")
    buildUponDefaultConfig = true
    autoCorrect = true
    config.setFrom("$rootDir/detekt/detektConfig.yml")
    basePath = rootProject.projectDir.absolutePath
}

tasks.withType<Detekt>().configureEach {
    reports {
        sarif {
            required.set(true)
        }
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "21"
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "21"
}

android {
    namespace = "com.haghpanh.pienote"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.haghpanh.pienote"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        lint {
            sarifReport = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.orNull
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.bundles.compose)
    implementation(libs.coil)
    implementation(libs.pagingCommon)
    implementation(libs.pagingCompose)
    implementation(kotlin("reflect"))

    //koin
    implementation(libs.bundles.koin)
    implementation(libs.koin.annotations)
    ksp(libs.koin.ksp.compiler)

    //material
    implementation(libs.material)

    //room
    implementation(libs.bundles.room)
    ksp(libs.roomCompiler)
    implementation(libs.paging)
    implementation(libs.room.paging)

    //lifecycle
    implementation(libs.bundles.lifecycle)
    ksp(libs.lifecycle.compiler)

    //detekt
    detektPlugins(libs.detekt.formatting)
}