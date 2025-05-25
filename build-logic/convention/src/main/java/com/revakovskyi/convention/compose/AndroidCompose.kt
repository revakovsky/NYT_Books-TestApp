package com.revakovskyi.convention.compose

import com.android.build.api.dsl.CommonExtension
import com.revakovskyi.convention.application.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * Configures Jetpack Compose support for Android **application** modules.
 *
 * This function builds on top of [configureAndroidLibraryCompose] to enable Compose,
 * and additionally includes navigation dependencies (e.g., `androidx.navigation`).
 *
 * Typically used inside a Gradle convention plugin for application modules that use Compose.
 *
 * @param commonExtension The Android application module extension.
 */
internal fun Project.configureAndroidApplicationCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    configureAndroidLibraryCompose(commonExtension)

    commonExtension.run {
        dependencies {
            "implementation"(libs.findBundle("navigation").get())
        }
    }
}


/**
 * Configures Jetpack Compose support for Android **library** modules.
 *
 * Enables the Compose build feature and adds:
 * - Compose BOM for version alignment
 * - Core Compose libraries (`compose` bundle)
 * - Debug tooling support (`androidx-ui-tooling`)
 *
 * Typically used inside convention plugins for UI or shared modules that use Compose.
 *
 * @param commonExtension The Android library or application module extension.
 */
internal fun Project.configureAndroidLibraryCompose(
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
