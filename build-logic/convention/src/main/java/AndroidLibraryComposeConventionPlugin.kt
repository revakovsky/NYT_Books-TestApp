import com.android.build.api.dsl.LibraryExtension
import com.revakovskyi.convention.compose.configureAndroidLibraryCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle convention plugin for configuring Android library modules that use Jetpack Compose.
 *
 * This plugin is intended for modules such as `ui`, `design_system`, `theme`, or any shared UI-related module
 * that leverages Jetpack Compose.
 *
 * This plugin:
 * - Applies the base [AndroidLibraryConventionPlugin] (`nyt_books.android.library`)
 * - Enables Jetpack Compose build features via [configureAndroidLibraryCompose]
 * - Configures dependencies with Compose BOM and standard Compose libraries
 * - Adds tooling support for Compose preview/debug builds
 *
 * This setup ensures consistent Jetpack Compose support across all Android library modules.
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("nyt_books.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.getByType<LibraryExtension>().also { extension ->
                configureAndroidLibraryCompose(extension)
            }
        }
    }

}