package com.revakovskyi.core.database.tools

import android.database.sqlite.SQLiteFullException
import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
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
suspend inline fun <T> safeDbCall(
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
