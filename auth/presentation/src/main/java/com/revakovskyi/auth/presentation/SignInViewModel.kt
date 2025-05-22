package com.revakovskyi.auth.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.revakovskyi.auth.presentation.auth_client.AuthClient
import com.revakovskyi.auth.presentation.auth_client.google.GoogleCredentialManager
import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.domain.auth.AuthProvider
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.connectivity.InternetStatus
import com.revakovskyi.core.domain.util.Result
import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel
import com.revakovskyi.core.presentation.utils.text_converters.toUiText
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SignInViewModel(
    savedStateHandle: SavedStateHandle,
    private val connectivityObserver: ConnectivityObserver,
    private val authClient: AuthClient,
) : BaseViewModel<SignInState, SignInAction, SignInEvent>(SignInState()) {

    private var credentialManager: GoogleCredentialManager? = null


    init {
        val forceSignOut = savedStateHandle["forceSignOut"] ?: false
        if (forceSignOut) {
            sendEvent(SignInEvent.RequestCredentialManager)
        }
    }


    override fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.InitGoogleCredentialManager -> initGoogleCredentialManager(action.manager)
            is SignInAction.SignIn -> checkConnection(action.authProvider)
            is SignInAction.ForceSignOut -> forceSignOut(action.manager)
        }
    }

    private fun initGoogleCredentialManager(manager: GoogleCredentialManager) {
        credentialManager = manager
        updateState { it.copy(isCredentialManagerInitialized = true) }
    }

    private fun checkConnection(authProvider: AuthProvider) {
        viewModelScope.launch {
            when (val internetStatus = connectivityObserver.internetStatus.first()) {
                InternetStatus.AVAILABLE, InternetStatus.LOSING -> {
                    when (authProvider) {
                        AuthProvider.GOOGLE -> signInWithGoogle()
                    }
                }

                else -> sendEvent(SignInEvent.AuthError(internetStatus.toUiText()))
            }
        }
    }

    private suspend fun signInWithGoogle() {
        updateState { it.copy(isSigningIn = true) }

        credentialManager?.let { manager ->
            when (val result = authClient.signIn(manager)) {
                is Result.Error -> sendEvent(SignInEvent.AuthError(result.error.toUiText()))
                is Result.Success -> getSignedInUser()
            }
        } ?: sendEvent(SignInEvent.AuthError(AuthError.Google.CREDENTIAL_FETCH_FAILED.toUiText()))

        updateState { it.copy(isSigningIn = false) }
    }

    private fun getSignedInUser() {
        authClient.getSignedInUser()?.let { user ->
            // Here we can save signed in user data into the db
            sendEvent(SignInEvent.SignInSuccess)
        } ?: sendEvent(SignInEvent.AuthError(AuthError.Google.CREDENTIAL_FETCH_FAILED.toUiText()))
    }

    private fun forceSignOut(manager: GoogleCredentialManager) {
        viewModelScope.launch {
            when (val result = authClient.signOut(manager)) {
                is Result.Error -> sendEvent(SignInEvent.AuthError(result.error.toUiText()))
                is Result.Success -> sendEvent(SignInEvent.SignOutSuccess)
            }
        }
    }


    override fun onCleared() {
        credentialManager = null
        super.onCleared()
    }

}
