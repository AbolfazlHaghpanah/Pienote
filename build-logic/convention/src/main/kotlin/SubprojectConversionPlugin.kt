import com.haghpanah.pienote.Configuration
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import java.io.File

class SubprojectConversionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("detekt").get().get().pluginId)
            extensions.findByType<DetektExtension>()?.apply {
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
                        outputLocation.set(File("$rootDir/build/reports/detekt", "detekt.sarif"))
                    }
                }
                jvmTarget = Configuration.JVM_TARGET.target
            }

            tasks.withType<DetektCreateBaselineTask>().configureEach {
                jvmTarget = Configuration.JVM_TARGET.target
            }
        }
    }
}