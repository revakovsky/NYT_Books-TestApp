package com.revakovskyi.core.domain.utils

/**
 * A sealed interface representing the outcome of an operation, which can either be a [Success] or an [Error].
 *
 * @param D The type of the successful result data.
 * @param E The type of the error, must extend [Error].
 */
sealed interface Result<out D, out E : Error> {

    /**
     * Represents a successful result.
     * @param data The data returned from the successful operation.
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed result.
     * @param error The error that occurred during the operation.
     */
    data class Error<out E : com.revakovskyi.core.domain.utils.Error>(val error: E) : Result<Nothing, E>

}


/**
 * Transforms the successful data [T] of the [Result] into a different type [R], keeping the error intact if present.
 *
 * @param map The transformation function to apply to the data.
 * @return A [Result] containing either the mapped success value or the same error.
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
    }
}


/**
 * A type alias representing a [Result] that either returns no data (`Unit`) on success or an error of type [E].
 */
typealias EmptyDataResult<E> = Result<Unit, E>


/**
 * Converts a [Result] with any data type to an [EmptyDataResult], discarding the data if successful.
 *
 * Useful when you care only about whether the operation was successful or failed, not the actual data.
 *
 * Example:
 * ```
 * val result: Result<String, AuthError> = Result.Success("Logged in")
 * val emptyResult: EmptyDataResult<AuthError> = result.asEmptyDataResult()
 * // emptyResult is now Result.Success(Unit)
 * ```
 *
 * Or when failed:
 * ```
 * val errorResult: Result<String, AuthError> = Result.Error(AuthError.Google.CREDENTIAL_FETCH_FAILED)
 * val emptyErrorResult = errorResult.asEmptyDataResult()
 * // emptyErrorResult is still Result.Error(AuthError.Google.CREDENTIAL_FETCH_FAILED)
 * ```
 */
fun <T, E : Error> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> = map { }


/**
 * Creates a successful [EmptyDataResult] with [Unit] as the result.
 *
 * Useful when you want to return a success result without any actual data.
 *
 * @return A [Result.Success] containing [Unit].
 *
 * Example:
 * ```
 * fun logout(): EmptyDataResult<AuthError> {
 *     firebaseAuth.signOut()
 *     return successfulResult()
 * }
 * ```
 *
 * In this example, you signal that the logout was successful, but there's no payload to return.
 */
fun <E : Error> successfulResult(): EmptyDataResult<E> = Result.Success(Unit)
