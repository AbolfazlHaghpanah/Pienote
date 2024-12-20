import com.android.build.api.dsl.ApplicationExtension
import com.haghpanah.pienote.Configuration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

class ApplicationGradlePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            println("mmd is Coming")

            pluginManager.apply(libs.findPlugin("androidApplication").get().get().pluginId)

            extensions.findByType<ApplicationExtension>()?.configureApplication()

            println("mmd done his job..")
        }
    }
}

private fun ApplicationExtension.configureApplication() {
    compileSdk = Configuration.COMPILE_SDK

    defaultConfig {
        minSdk = Configuration.MIN_SDK
        targetSdk = Configuration.TARGET_SDK
        versionCode = Configuration.VERSION_CODE
        versionName = Configuration.VERSION_NAME
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
