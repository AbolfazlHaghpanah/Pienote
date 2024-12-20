package com.haghpanah.pienote

import org.gradle.api.JavaVersion

object Configuration {
    const val COMPILE_SDK = 34
    const val MIN_SDK = 24
    const val TARGET_SDK = 24
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"
    val JVM_NAME = JavaVersion.VERSION_21
    const val JVM_CODE = 21
}