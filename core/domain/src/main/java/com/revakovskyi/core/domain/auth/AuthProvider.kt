package com.revakovskyi.core.domain.auth

/**
 * Represents the available authentication providers supported by the application.
 *
 * This enum is typically used to indicate which provider the user chooses or which flow should be triggered.
 *
 * ## Example:
 * ```kotlin
 * when (provider) {
 *     AuthProvider.GOOGLE -> handleGoogleSignIn()
 *     AuthProvider.EMAIL -> handleEmailSignIn()
 * }
 * ```
 */
enum class AuthProvider {
    GOOGLE,
}
