import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class ComposeMultiplatformConversionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            pluginManager.apply(libs.findPlugin("pienote-multiplatform").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("composeMultiplatform").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("composeCompiler").get().get().pluginId)

            extensions.findByType<KotlinMultiplatformExtension>()?.apply {
                sourceSets.androidMain.dependencies {
                    implementation(compose("org.jetbrains.compose.ui:ui-tooling-preview"))
                }
                sourceSets.commonMain.dependencies {
                    implementation(libs.findLibrary("compose.runtime").get().get())
                    implementation(libs.findLibrary("compose.material3").get().get())
                    implementation(
                        libs.findLibrary("compose.material3.adaptive.navigation").get().get()
                    )
                    implementation(libs.findLibrary("compose.ui").get().get())
                    implementation(libs.findLibrary("compose.foundation").get().get())
                    implementation(libs.findLibrary("compose.components.resources").get().get())
                    implementation(
                        libs.findLibrary("compose.components.uiToolingPreview").get().get()
                    )
                    implementation(libs.findLibrary("compose.animation.graphics").get().get())
                    implementation(libs.findLibrary("compose.animation").get().get())
                }
            }
        }
    }
}
