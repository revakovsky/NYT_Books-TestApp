package com.revakovskyi.core.domain.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provides abstraction over different [CoroutineDispatcher]s used in the application.
 *
 * This interface allows for decoupling coroutine context specification from implementation details,
 * making testing and concurrency control more flexible.
 *
 * ## Use Cases
 * - Enables injecting test dispatchers (e.g., TestDispatcher) for unit and integration testing.
 * - Allows consistent usage of dispatchers across the app.
 *
 * ## Example
 * ```kotlin
 * class MyRepository(private val dispatcherProvider: DispatcherProvider) {
 *     suspend fun fetchData() = withContext(dispatcherProvider.io) {
 *         // Perform network or database operation
 *     }
 * }
 * ```
 */
interface DispatcherProvider {

    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val unconfined: CoroutineDispatcher

}
