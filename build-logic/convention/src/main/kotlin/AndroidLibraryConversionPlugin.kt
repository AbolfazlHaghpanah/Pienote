import com.android.build.api.dsl.LibraryExtension
import com.haghpanah.pienote.configureCommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryConversionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            pluginManager.apply(libs.findPlugin("androidLibrary").get().get().pluginId)

            extensions.findByType<LibraryExtension>()?.configureCommonExtension()
        }
    }
}
