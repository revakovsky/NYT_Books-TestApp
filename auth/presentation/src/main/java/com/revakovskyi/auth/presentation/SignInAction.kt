package com.revakovskyi.auth.presentation

import com.revakovskyi.auth.presentation.auth_client.google.GoogleCredentialManager

sealed interface SignInAction {

    data object SignInWithGoogle : SignInAction
    data class InitGoogleCredentialManager(val manager: GoogleCredentialManager) : SignInAction
    data class ForceSignOut(val manager: GoogleCredentialManager) : SignInAction

}
