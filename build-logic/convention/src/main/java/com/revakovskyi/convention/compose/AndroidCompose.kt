package com.revakovskyi.convention.compose

import com.android.build.api.dsl.CommonExtension
import com.revakovskyi.convention.application.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidApplicationCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    configureAndroidCompose(commonExtension)

    commonExtension.run {
        dependencies {
            "implementation"(libs.findBundle("navigation").get())
        }
    }
}


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.run {
        buildFeatures {
            compose = true
        }

        dependencies {
            val composeBom = libs.findLibrary("androidx.compose.bom").get()

            "implementation"(platform(composeBom))
            "implementation"(libs.findBundle("compose").get())

            "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
        }
    }
}
