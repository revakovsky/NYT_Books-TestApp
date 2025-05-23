package com.revakovskyi.core.domain.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Provides standard [CoroutineDispatcher] implementations from Kotlin Coroutines.
 *
 * This implementation of [DispatcherProvider] exposes commonly used dispatchers:
 * - [Dispatchers.Main] for UI-related work
 * - [Dispatchers.IO] for I/O operations (network, database, file I/O)
 * - [Dispatchers.Default] for CPU-intensive work (sorting, parsing, computation)
 * - [Dispatchers.Unconfined] for advanced coroutine control (not recommended for general use)
 *
 * Useful for abstracting dispatchers in production code, especially to allow swapping
 * them with test dispatchers in unit or instrumentation tests.
 *
 * Example:
 * ```
 * class SomeRepository(
 *     private val dispatcherProvider: DispatcherProvider = StandardDispatchers()
 * ) {
 *     suspend fun loadData() = withContext(dispatcherProvider.io) {
 *         // perform I/O operation
 *     }
 * }
 * ```
 */
class StandardDispatchers : DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default

    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined

}
