package com.revakovskyi.convention.application

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

private const val API_KEY = "API_KEY"
private const val API_KEY_VALUE = "JNd7LkmFEYe6WelU5YG2FwZAXAeGCAH4"

private const val BASE_URL = "BASE_URL"
private const val BASE_URL_VALUE = "https://api.nytimes.com/svc/books/v3"


/**
 * Configures `debug` and `release` build types for either an application or library module.
 *
 * This function:
 * - Enables `BuildConfig` generation
 * - Loads `apiKey` and `baseUrl` from local Gradle properties
 * - Applies build type configuration based on the [extensionType] (application or library)
 *
 * @param commonExtension The shared [CommonExtension] for configuring Android builds.
 * @param extensionType Type of the module being configured ([ExtensionType.APPLICATION] or [ExtensionType.LIBRARY]).
 */
internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType,
) {
    commonExtension.run {
        buildFeatures { buildConfig = true }

        val apiKey = gradleLocalProperties(
            rootDir,
            rootProject.providers
        ).getProperty(API_KEY)

        val baseUrl = gradleLocalProperties(
            rootDir,
            rootProject.providers
        ).getProperty(BASE_URL)

        when (extensionType) {
            ExtensionType.APPLICATION -> setUpApplicationBuildTypes(apiKey, baseUrl, commonExtension)
            ExtensionType.LIBRARY -> setUpLibraryBuildTypes(apiKey, baseUrl, commonExtension)
        }
    }
}


/**
 * Configures build types for Android application modules.
 *
 * Sets up:
 * - `debug` and `release` build types
 * - Default `BuildConfig` fields (API key and base URL)
 * - ProGuard for release builds
 */
private fun Project.setUpApplicationBuildTypes(
    apiKey: String,
    baseUrl: String,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    extensions.configure<ApplicationExtension> {
        buildTypes {
            debug { configureDebugBuildType(apiKey, baseUrl) }
            release { configureReleaseBuildType(commonExtension, apiKey, baseUrl) }
        }
    }
}


/**
 * Configures `debug` and `release` build types for Android library modules.
 *
 * This setup mirrors [setUpApplicationBuildTypes], but is specifically tailored for modules
 * using [LibraryExtension]. It applies standard BuildConfig fields (e.g., API key and base URL)
 * and optionally configures ProGuard rules for release builds.
 *
 * Typically used in library modules that require runtime configuration via `BuildConfig`.
 */
private fun Project.setUpLibraryBuildTypes(
    apiKey: String,
    baseUrl: String,
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    extensions.configure<LibraryExtension> {
        buildTypes {
            debug { configureDebugBuildType(apiKey, baseUrl) }
            release { configureReleaseBuildType(commonExtension, apiKey, baseUrl) }
        }
    }
}


/**
 * Adds API key and base URL to `BuildConfig` for the `debug` build type.
 *
 * @param apiKey The API key to embed in the build.
 * @param baseUrl The base URL to embed in the build.
 */
private fun BuildType.configureDebugBuildType(apiKey: String, baseUrl: String) {
    // In the project used plugs because this is a test project!
    buildConfigField("String", API_KEY, "\"$API_KEY_VALUE\"")
    buildConfigField("String", BASE_URL, "\"$BASE_URL_VALUE\"")
}


/**
 * Adds API key and base URL to `BuildConfig`, enables minification, and applies ProGuard rules for `release` builds.
 *
 * @param commonExtension Common Android extension for access to default ProGuard file.
 * @param apiKey The API key to embed in the build.
 * @param baseUrl The base URL to embed in the build.
 */
private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String,
    baseUrl: String,
) {
    // In the project used plugs because this is a test project!
    buildConfigField("String", API_KEY, "\"$API_KEY_VALUE\"")
    buildConfigField("String", BASE_URL, "\"$BASE_URL_VALUE\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}
