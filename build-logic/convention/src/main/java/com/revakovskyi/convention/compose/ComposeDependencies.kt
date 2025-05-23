package com.revakovskyi.convention.compose

import com.revakovskyi.convention.application.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * Adds a standard set of UI layer dependencies for a feature module.
 *
 * Typically used in Gradle convention plugins to configure `feature` modules
 * that utilize Jetpack Compose and Android Lifecycle components.
 *
 * This helper adds the following dependency bundles:
 * - Compose core libraries (`compose`)
 * - Compose debug tooling (`compose.debug`)
 * - Lifecycle support libraries (`lifecycle`)
 *
 * @param project The [Project] instance used to access the version catalog (`libs`).
 */
fun DependencyHandlerScope.addFeatureUiLayerDependencies(project: Project) {

    "implementation"(project.libs.findBundle("compose").get())
    "implementation"(project.libs.findBundle("compose.debug").get())
    "implementation"(project.libs.findBundle("lifecycle").get())

}
