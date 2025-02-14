import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class FeatureConversionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            pluginManager.apply {
                apply(libs.findPlugin("pienote-compose-multiplatform").get().get().pluginId)
                apply(libs.findPlugin("kotlin-serialization").get().get().pluginId)
                apply(libs.findPlugin("pienote-subproject").get().get().pluginId)
            }

            extensions.findByType<KotlinMultiplatformExtension>()?.apply {
                sourceSets.commonMain.dependencies {
                    implementation(libs.findLibrary("uri.kmp").get().get())
                    implementation(libs.findLibrary("compose.ui.util").get().get())
                    implementation(libs.findLibrary("androidx.lifecycle.viewmodel").get().get())
                    implementation(libs.findLibrary("androidx.lifecycle.runtime.compose").get().get())
                    implementation(libs.findLibrary("androidx.navigation.compose").get().get())
                    implementation(libs.findLibrary("compose.material.icons").get().get())
                    implementation(libs.findLibrary("kotlin.serialization").get().get())
                    implementation(libs.findLibrary("kotlin.reflect").get().get())
                    implementation(libs.findLibrary("coil.compose").get().get())
                    implementation(libs.findLibrary("koin.core").get().get())
                    implementation(libs.findLibrary("koin.viewmodel").get().get())
                    implementation(libs.findLibrary("koin.viewmodel.navigation").get().get())
                    implementation(project(":ui:navigation"))
                    implementation(project(":ui:design-system"))
                    implementation(project(":ui:snackbar"))
                    implementation(project(":ui:base"))
                    implementation(project(":domain"))
                }
            }
        }
    }
}