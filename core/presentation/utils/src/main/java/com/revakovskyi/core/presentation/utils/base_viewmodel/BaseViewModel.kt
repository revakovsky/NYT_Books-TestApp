package com.revakovskyi.core.presentation.utils.base_viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * BaseViewModel is the most feature-complete base class combining state, actions, and one-time events.
 *
 * This class builds upon [BaseStateActionViewModel] by adding support for single-shot UI events,
 * such as navigation, toasts, or dialogs, through the [event] Flow.
 *
 * @param S The type of the UI state.
 * @param A The type representing user or system actions.
 * @param E The type representing one-time events sent to the UI.
 * @param initialState The initial value of the state.
 */
abstract class BaseViewModel<S, A, E>(
    initialState: S,
) : BaseStateActionViewModel<S, A>(initialState) {

    private val _event = Channel<E>(Channel.BUFFERED)
    val event: Flow<E> = _event.receiveAsFlow()

    /**
     * Sends a one-time UI event of type [E] using a [Channel].
     * This is useful for things like showing a Snackbar, triggering navigation, etc.
     */
    protected fun sendEvent(event: E) {
        viewModelScope.launch {
            _event.trySend(event)
        }
    }

}
