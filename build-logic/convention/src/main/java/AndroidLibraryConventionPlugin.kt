import com.android.build.api.dsl.LibraryExtension
import com.revakovskyi.convention.application.ExtensionType
import com.revakovskyi.convention.application.configureBuildTypes
import com.revakovskyi.convention.application.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

/**
 * Gradle convention plugin for configuring Android library modules.
 *
 * This plugin is intended for configuring modules such as `data`, `network`, `database`,
 * and other shared library modules within the project.
 *
 * This plugin:
 * - Applies the `com.android.library` and `org.jetbrains.kotlin.android` plugins
 * - Configures standard Kotlin and Android compile settings
 * - Sets up default test instrumentation runner and consumer ProGuard rules
 * - Delegates Kotlin configuration to [configureKotlinAndroid]
 * - Applies standard build types via [configureBuildTypes]
 * - Adds basic test dependencies (`kotlin("test")`)
 *
 * This plugin helps enforce a consistent setup across all Android library modules
 * in the project and reduces duplication in Gradle build scripts.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                configureBuildTypes(
                    commonExtension = this@configure,
                    extensionType = ExtensionType.LIBRARY
                )
            }

            dependencies {
                "testImplementation"(kotlin("test"))
            }
        }
    }

}