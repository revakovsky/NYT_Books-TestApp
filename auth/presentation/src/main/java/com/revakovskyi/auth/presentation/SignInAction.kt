package com.revakovskyi.auth.presentation

import com.revakovskyi.auth.presentation.auth_client.google.GoogleCredentialManager
import com.revakovskyi.core.domain.auth.AuthProvider

sealed interface SignInAction {

    data class SignIn(val authProvider: AuthProvider) : SignInAction
    data class InitGoogleCredentialManager(val manager: GoogleCredentialManager) : SignInAction
    data class ForceSignOut(val manager: GoogleCredentialManager) : SignInAction

}
