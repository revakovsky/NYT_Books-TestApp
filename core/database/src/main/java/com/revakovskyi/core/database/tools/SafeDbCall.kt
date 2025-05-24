package com.revakovskyi.core.database.tools

import android.database.sqlite.SQLiteFullException
import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes a local database operation safely on the provided [dispatcher].
 *
 * This utility method ensures that any thrown exceptions during the database operation
 * are caught and mapped to a sealed [DataError.Local] error type.
 *
 * - Throws [CancellationException] as-is to avoid breaking coroutine cancellation flow.
 * - Catches [SQLiteFullException] to handle cases where the disk is full.
 * - Catches all other exceptions as unknown database errors.
 * - All results are wrapped in a sealed [Result] class to indicate success or failure.
 *
 * Example usage:
 * ```
 * val result = safeDbCall(dispatcherProvider.io) {
 *     database.insert(item)
 * }
 * ```
 *
 * @param dispatcher The [CoroutineDispatcher] on which the operation should be executed (typically `Dispatchers.IO`).
 * @param action The suspend lambda representing the database operation.
 *
 * @return A [Result] containing either the result of the operation or a [DataError.Local] error.
 */
internal suspend inline fun <T> safeDbCall(
    dispatcher: CoroutineDispatcher,
    crossinline action: suspend () -> T,
): Result<T, DataError.Local> {
    return withContext(dispatcher) {
        try {
            Result.Success(action())
        } catch (e: SQLiteFullException) {
            Timber.e(e, "SQLiteFullException")
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Timber.e(e, "DB error")
            if (e is CancellationException) throw e
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}


/**
 * Executes a local database [Flow] operation safely on the provided [dispatcher].
 *
 * This utility function wraps a database [Flow] stream into a [Result] container,
 * capturing and mapping any thrown exceptions into a sealed [DataError.Local] type.
 *
 * - Each emitted item is wrapped in a [Result.Success].
 * - Throws [CancellationException] as-is to support coroutine cancellation propagation.
 * - Catches [SQLiteFullException] to indicate disk space exhaustion.
 * - Catches all other exceptions and maps them to [DataError.Local.UNKNOWN].
 * - The operation runs on the specified [dispatcher] using [flowOn].
 *
 * Example usage:
 * ```
 * val flowResult: Flow<Result<List<Item>, DataError.Local>> =
 *     safeDbFlowCall(dispatcherProvider.io) {
 *         database.getItemsFlow()
 *     }
 * ```
 *
 * @param dispatcher The [CoroutineDispatcher] to execute the flow on (usually `Dispatchers.IO`).
 * @param action A lambda returning a [Flow] of data from the database.
 *
 * @return A [Flow] that emits [Result] instances indicating success or failure of the DB stream.
 */
internal fun <T> safeDbFlowCall(
    dispatcher: CoroutineDispatcher,
    action: () -> Flow<T>,
): Flow<Result<T, DataError.Local>> {
    return action()
        .map<T, Result<T, DataError.Local>> { Result.Success(it) }
        .catch { exception ->
            val error: Result.Error<DataError.Local> = when (exception) {
                is SQLiteFullException -> {
                    Timber.e(exception, "SQLiteFullException")
                    Result.Error(DataError.Local.DISK_FULL)
                }

                is CancellationException -> throw exception

                else -> {
                    Timber.e(exception, "DB error")
                    Result.Error(DataError.Local.UNKNOWN)
                }
            }
            emit(error)
        }.flowOn(dispatcher)
}
