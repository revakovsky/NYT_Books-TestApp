import com.android.build.api.dsl.ApplicationExtension
import com.revakovskyi.convention.compose.configureAndroidApplicationCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle convention plugin for configuring Android application modules that use Jetpack Compose.
 *
 * This plugin:
 * - Applies the base [AndroidApplicationConventionPlugin] (`nyt_books.android.application`)
 * - Applies the Compose Kotlin plugin (`org.jetbrains.kotlin.plugin.compose`)
 * - Enables Jetpack Compose build features via [configureAndroidApplicationCompose]
 * - Adds required Compose dependencies (Compose BOM, core UI libraries, tooling)
 * - Adds navigation dependencies using a version bundle defined in the version catalog
 *
 * This plugin centralizes and simplifies Compose-related setup for application modules,
 * ensuring consistency across the project and reducing the need for duplicate Gradle code.
 */
class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply {
                apply("nyt_books.android.application")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            val commonExtension = extensions.getByType<ApplicationExtension>()
            configureAndroidApplicationCompose(commonExtension)
        }
    }

}