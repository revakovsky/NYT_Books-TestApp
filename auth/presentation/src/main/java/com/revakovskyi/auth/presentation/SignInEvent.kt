package com.revakovskyi.auth.presentation

import com.revakovskyi.core.presentation.utils.UiText

sealed interface SignInEvent {

    data object SignInSuccess : SignInEvent
    data object SignOutSuccess : SignInEvent
    data object RequestCredentialManager : SignInEvent
    data class AuthError(val message: UiText) : SignInEvent

}
