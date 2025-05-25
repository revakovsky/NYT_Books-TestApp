package com.revakovskyi.convention.application

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private val javaVersion = JavaVersion.VERSION_11
private val localJvmTarget = JvmTarget.JVM_11


/**
 * Configures Kotlin and Android-specific settings for modules that use Android Gradle Plugin.
 *
 * This includes:
 * - Setting the compile SDK version and minimum SDK version from version catalog
 * - Setting Java compatibility to [JavaVersion.VERSION_11]
 * - Delegating further Kotlin compiler configuration to [configureKotlin]
 *
 * Should be used in convention plugins for Android modules (application or library).
 *
 * @param commonExtension The [CommonExtension] instance for the module.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = libs.findVersion("compileSdk").get().toString().toInt()
        defaultConfig.minSdk = libs.findVersion("minSdk").get().toString().toInt()

        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }
    }

    configureKotlin()
}


/**
 * Configures Kotlin compiler and Java compatibility for JVM-only modules (non-Android).
 *
 * - Sets source and target compatibility to [JavaVersion.VERSION_11]
 * - Delegates to [configureKotlin] for Kotlin-specific compiler settings
 */
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    configureKotlin()
}


/**
 * Configures Kotlin compiler options for all Kotlin compilation tasks.
 *
 * Sets the JVM target to [JvmTarget.JVM_11] for consistency across modules.
 */
internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(localJvmTarget)
        }
    }
}
