package com.revakovskyi.auth.presentation

import com.revakovskyi.core.presentation.utils.UiText

sealed interface SignInEvent {

    data object SignInSuccess : SignInEvent
    data object SignOutSuccess : SignInEvent
    data class SignInError(val message: UiText) : SignInEvent

}
