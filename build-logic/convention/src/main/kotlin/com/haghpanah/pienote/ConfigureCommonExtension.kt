package com.haghpanah.pienote

import com.android.build.api.dsl.CommonExtension

fun CommonExtension<*, *, *, *, *, *>.configureCommonExtension() {
    compileSdk = Configuration.COMPILE_SDK

    defaultConfig {
        minSdk = Configuration.MIN_SDK
    }

    packaging.resources.excludes.add("/META-INF/{AL2.0,LGPL2.1}")

    buildTypes.getByName("release") {
        isMinifyEnabled = true
    }

    compileOptions {
        sourceCompatibility = Configuration.JVM_NAME
        targetCompatibility = Configuration.JVM_NAME
    }
}
