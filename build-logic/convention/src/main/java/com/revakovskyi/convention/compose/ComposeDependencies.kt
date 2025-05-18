package com.revakovskyi.convention.compose

import com.revakovskyi.convention.application.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addUiLayerDependencies(project: Project) {

    "implementation"(project.libs.findBundle("compose").get())
    "implementation"(project.libs.findBundle("compose.debug").get())
    "implementation"(project.libs.findBundle("lifecycle").get())

}
