package com.revakovskyi.core.domain.utils

/**
 * Represents a sealed hierarchy of application-level error types used across network and local operations.
 *
 * This sealed interface is divided into specific categories of errors to provide better type safety and clarity
 * when handling failures, especially in result wrappers like [Result].
 *
 * ## Usage
 * You can use `DataError.Network` for network-related operations and `DataError.Local` for local database or
 * storage-related failures.
 *
 * ## Example
 * ```kotlin
 * fun handleError(error: DataError) {
 *     when (error) {
 *         DataError.Network.NO_INTERNET -> showToast("No internet connection")
 *         DataError.Local.DISK_FULL -> showDialog("Storage is full")
 *         else -> logUnhandled(error)
 *     }
 * }
 * ```
 */
sealed interface DataError : Error {

    enum class Network : DataError {
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN,
    }

}
