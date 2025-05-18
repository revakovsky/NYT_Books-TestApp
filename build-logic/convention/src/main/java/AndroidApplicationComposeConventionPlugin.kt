import com.android.build.api.dsl.ApplicationExtension
import com.revakovskyi.convention.compose.configureAndroidApplicationCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

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