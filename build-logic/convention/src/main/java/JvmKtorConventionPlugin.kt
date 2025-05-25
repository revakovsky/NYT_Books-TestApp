import com.revakovskyi.convention.application.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for configuring JVM modules that use Ktor for networking.
 *
 * This plugin is intended for modules such as `network`, `remote`, or any backend communication
 * layer that leverages Ktor.
 *
 * This plugin:
 * - Applies the `org.jetbrains.kotlin.plugin.serialization` plugin to support Kotlinx Serialization
 * - Adds core Ktor dependencies via the `ktor` bundle from the version catalog
 *
 * This setup ensures a consistent Ktor client configuration across all relevant JVM modules.
 */
class JvmKtorConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                "implementation"(libs.findBundle("ktor").get())
            }
        }
    }

}