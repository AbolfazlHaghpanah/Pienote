import com.android.build.api.dsl.ApplicationExtension
import com.haghpanah.pienote.Configuration
import com.haghpanah.pienote.configureCommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

class MultiplatformApplicationConversionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("androidApplication").get().get().pluginId)
            pluginManager.apply(libs.findPlugin("pienote-compose-multiplatform").get().get().pluginId)

            extensions.findByType<ApplicationExtension>()?.apply {
                configureCommonExtension()

                defaultConfig {
                    targetSdk = Configuration.TARGET_SDK
                    versionCode = Configuration.VERSION_CODE
                    versionName = Configuration.VERSION_NAME
                }
            }
        }
    }
}
