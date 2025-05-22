package com.revakovskyi.core.presentation.utils.base_viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * BaseStateViewModel is a minimal ViewModel that manages only UI state.
 *
 * This class provides a [StateFlow] to expose the current state and a convenient method [updateState]
 * to mutate the state using a reducer-style lambda.
 *
 * @param S The type of the UI state.
 * @param initialState The initial value of the state.
 */
abstract class BaseStateViewModel<S>(initialState: S) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    /**
     * Updates the current state using the given [update] lambda.
     * This follows a reducer pattern where the new state is based on the previous one.
     */
    protected fun updateState(update: (S) -> S) {
        _state.update(update)
    }

}
