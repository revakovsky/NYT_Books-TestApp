package com.revakovskyi.core.domain.utils

/**
 * Marker interface representing an error type used in domain-level [Result] wrappers.
 *
 * This interface is designed to provide a common abstraction for different categories of errors,
 * such as:
 * - [DataError.Network] for network-related failures
 * - [DataError.Local] for local storage/database issues
 * - [AuthError] for authentication failures
 *
 * All custom error types used in `Result<Success, Error>` structures should implement this interface
 * to provide consistency and type safety across the domain layer.
 *
 * Example:
 * ```
 * sealed class DataError : Error {
 *     object NETWORK : DataError()
 *     object UNKNOWN : DataError()
 * }
 * ```
 */
interface Error
