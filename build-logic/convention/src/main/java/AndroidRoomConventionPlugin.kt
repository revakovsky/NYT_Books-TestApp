import androidx.room.gradle.RoomExtension
import com.revakovskyi.convention.application.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

/**
 * Gradle convention plugin for configuring Android modules that use Room for local database storage.
 *
 * This plugin is intended for modules such as `database` or any layer responsible for
 * local persistence using Room.
 *
 * This plugin:
 * - Applies the `androidx.room` Gradle plugin
 * - Applies the Kotlin Symbol Processing (KSP) plugin via `com.google.devtools.ksp`
 * - Configures Room to export database schemas to the module's `/schemas` directory
 * - Adds Room runtime dependencies and annotation processor (`room.compiler`)
 *
 * This setup ensures consistent Room integration and schema management across modules.
 */
class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("androidx.room")
                apply("com.google.devtools.ksp")
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                "implementation"(libs.findBundle("room").get())
                "ksp"(libs.findLibrary("room.compiler").get())
            }
        }
    }

}