package com.revakovskyi.core.database.tools

import android.database.sqlite.SQLiteFullException
import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

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
