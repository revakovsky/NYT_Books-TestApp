package com.revakovskyi.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * Observes a one-time [Flow] of events and triggers the [onEvent] callback only when the lifecycle is in the `STARTED` state.
 *
 * This composable is useful for handling side effects like navigation, showing snackbars, dialogs, etc.,
 * that should occur in response to events emitted by a [ViewModel] or other state holder.
 *
 * It ensures that events are only handled when the UI is active (i.e., `STARTED`), avoiding potential issues
 * like triggering actions when the user is not on the screen.
 *
 * The [onEvent] block is called on the main thread, ensuring compatibility with UI operations.
 *
 * Example usage:
 * ```
 * ObserveSingleEvent(viewModel.event) { event ->
 *     when (event) {
 *         is UiEvent.ShowSnackbar -> showSnackbar(event.message)
 *         is UiEvent.NavigateTo -> navController.navigate(event.route)
 *     }
 * }
 * ```
 *
 * @param flow The [Flow] that emits events to be handled once.
 * @param key1 An optional key used to control when the effect should restart.
 * @param key2 An optional secondary key used to control when the effect should restart.
 * @param onEvent A suspend lambda triggered for each event emitted by the [flow].
 */
@Composable
fun <T> ObserveSingleEvent(
    flow: Flow<T>,
    key1: Any? = null,
    key2: Any? = null,
    onEvent: suspend (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(flow, lifecycleOwner.lifecycle, key1, key2) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            withContext(Dispatchers.Main.immediate) {
                flow.collect { event ->
                    onEvent(event)
                }
            }
        }
    }
}
