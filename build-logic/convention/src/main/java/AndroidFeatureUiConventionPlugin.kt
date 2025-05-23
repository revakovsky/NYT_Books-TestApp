import com.revakovskyi.convention.compose.addFeatureUiLayerDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for configuring feature-specific UI modules that use Jetpack Compose.
 *
 * This plugin is intended for feature UI modules such as `auth.presentation`, `books.presentation`, or any screen-specific presentation layer
 * that is part of the application's UI architecture.
 *
 * This plugin:
 * - Applies the base [AndroidLibraryComposeConventionPlugin] (`nyt_books.android.library.compose`)
 * - Adds core UI dependencies including:
 *   - Jetpack Compose libraries (`compose`)
 *   - Lifecycle-aware components (`lifecycle`)
 *   - Debug tooling for Compose (`compose.debug`)
 *
 * This setup ensures a consistent and minimal UI setup across all feature UI modules.
 */
class AndroidFeatureUiConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("nyt_books.android.library.compose")
            }

            dependencies {
                addFeatureUiLayerDependencies(target)
            }
        }
    }
}