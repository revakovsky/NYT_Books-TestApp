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
private const val BASE_URL_VALUE = "https://api.nytimes.com/svc/books/v3/"


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


private fun BuildType.configureDebugBuildType(apiKey: String, baseUrl: String) {
    // In the project used plugs because this is a test project!
    buildConfigField("String", API_KEY, "\"$API_KEY_VALUE\"")
    buildConfigField("String", BASE_URL, "\"$BASE_URL_VALUE\"")
}


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
