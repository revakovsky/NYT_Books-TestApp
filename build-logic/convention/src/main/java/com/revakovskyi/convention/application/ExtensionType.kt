package com.revakovskyi.convention.application

/**
 * Enum representing the type of Android module to be configured.
 *
 * Used to differentiate configuration logic between application and library modules
 * (e.g., when applying specific Gradle build types or ProGuard rules).
 */
internal enum class ExtensionType {
    APPLICATION, LIBRARY,
}
