import com.android.build.api.dsl.ApplicationExtension
import com.revakovskyi.convention.application.ExtensionType
import com.revakovskyi.convention.application.configureBuildTypes
import com.revakovskyi.convention.application.configureKotlinAndroid
import com.revakovskyi.convention.application.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

/**
 * Gradle convention plugin for configuring Android application modules.
 *
 * This plugin:
 * - Applies essential plugins: `com.android.application`, `org.jetbrains.kotlin.android`
 * - Configures common Android settings such as `applicationId`, `targetSdk`, `versionCode`, and `versionName`
 *   using centralized values from the version catalog (`libs.versions.toml`)
 * - Applies default Kotlin Android compiler settings and JVM target configuration (Java 11)
 * - Automatically sets `compileSdk` and `minSdk` values for the project
 * - Configures `BuildConfig` fields for `API_KEY` and `BASE_URL` from `local.properties`, allowing secure and environment-specific values
 * - Applies build type configuration for `debug` and `release`:
 *   - `debug` builds inject fixed `BuildConfigField` values (used as stubs in test environments)
 *   - `release` builds apply Proguard rules and enable code shrinking
 *
 * Additionally, this plugin integrates the shared Kotlin configuration logic via [configureKotlinAndroid]
 * and applies proper `JvmTarget` settings for Kotlin compilation tasks.
 *
 * This convention plugin helps maintain consistency, reduces boilerplate, and simplifies module-level build scripts.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("appId").get().toString()
                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()

                    versionCode = libs.findVersion("versionCode").get().toString().toInt()
                    versionName = libs.findVersion("versionName").get().toString()
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }

}