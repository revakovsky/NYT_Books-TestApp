package com.revakovskyi.core.presentation.utils.base_viewmodel

/**
 * BaseStateActionViewModel extends [BaseStateViewModel] and adds support for user or system actions.
 *
 * It defines an [onAction] method that can be overridden in subclasses to handle various
 * UI or domain actions without emitting one-time events.
 *
 * @param S The type of the UI state.
 * @param A The type representing user or system actions.
 * @param initialState The initial value of the state.
 */
abstract class BaseStateActionViewModel<S, A>(
    initialState: S,
) : BaseStateViewModel<S>(initialState) {

    /**
     * Handles an action of type [A]. Override this method in a subclass to respond to UI events or actions.
     * The default implementation does nothing.
     */
    open fun onAction(action: A) = Unit

}
