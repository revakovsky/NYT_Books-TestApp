package com.revakovskyi.core.database.tools

import android.database.sqlite.SQLiteFullException
import android.util.Log
import com.revakovskyi.core.domain.books.DataError
import com.revakovskyi.core.domain.util.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <T> safeDbCall(
    dispatcher: CoroutineDispatcher,
    crossinline action: suspend () -> T,
): Result<T, DataError.Local> {
    return withContext(dispatcher) {
        try {
            Result.Success(action())
        } catch (e: SQLiteFullException) {
            Log.e("SafeDbCall", "SQLiteFullException", e)
            Result.Error(DataError.Local.DISK_FULL)
        } catch (e: Exception) {
            Log.e("SafeDbCall", "DB error", e)
            if (e is CancellationException) throw e
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}
