import com.revakovskyi.convention.application.configureKotlinJvm
import com.revakovskyi.convention.application.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for configuring pure JVM/Kotlin library modules.
 *
 * This plugin is intended for modules such as `domain`, `model`, or any other non-Android
 * module that uses Kotlin on the JVM and may require serialization or coroutines.
 *
 * This plugin:
 * - Applies the `org.jetbrains.kotlin.jvm` and `org.jetbrains.kotlin.plugin.serialization` plugins
 * - Configures Kotlin JVM target and compilation settings via [configureKotlinJvm]
 * - Adds essential dependencies:
 *   - Kotlin Coroutines (`coroutines.android`)
 *   - Kotlinx Serialization (`kotlinx.serialization.json`)
 *
 * This setup ensures consistent JVM support and tooling across all core and utility modules.
 */
class JvmLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            configureKotlinJvm()

            dependencies {
                "implementation"(libs.findLibrary("coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
            }
        }
    }

}