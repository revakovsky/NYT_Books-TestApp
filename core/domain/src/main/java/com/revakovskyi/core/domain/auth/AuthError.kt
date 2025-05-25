package com.revakovskyi.core.domain.auth

import com.revakovskyi.core.domain.utils.Error

/**
 * Represents errors that can occur during the authentication process.
 *
 * This sealed interface defines authentication-related error types, which are grouped by
 * the source of the error: [Google] or [Firebase].
 *
 * ## Usage
 * Used as the error type in [Result] wrappers to provide structured error handling in authentication flows.
 *
 * ```kotlin
 * val result: Result<Unit, AuthError> = authClient.signIn(...)
 * when (result) {
 *     is Result.Success -> { /* Handle success */ }
 *     is Result.Error -> when (val error = result.error) {
 *         AuthError.Google.CREDENTIAL_FETCH_FAILED -> { /* Show specific message */ }
 *         AuthError.Firebase.INVALID_CREDENTIALS -> { /* Prompt for correct credentials */ }
 *         else -> { /* Handle unknown error */ }
 *     }
 * }
 * ```
 */
sealed interface AuthError : Error {

    enum class Google : AuthError {
        NO_GOOGLE_ACCOUNT,
        CREDENTIAL_FETCH_FAILED,
        UNSUPPORTED_CREDENTIAL_TYPE,
        CREDENTIAL_CLEAR_FAILED,
    }

    enum class Firebase : AuthError {
        INVALID_USER,
        INVALID_CREDENTIALS,
        ACCOUNT_COLLISION,
        UNKNOWN,
    }

}
