import com.haghpanah.pienote.Configuration

plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.pienote.subproject)
}

kotlin {
    jvmToolchain(Configuration.JVM_CODE)
}

dependencies {
    implementation(libs.uri.kmp)
    implementation(libs.kotlinx.coroutines.core)
}
