package com.revakovskyi.auth.presentation

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.revakovskyi.auth.presentation.auth_client.AuthClient
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


    init {
        val forceSignOut = savedStateHandle["forceSignOut"] ?: false
        if (forceSignOut) forceSignOut()
    }


    override fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.SignInWithGoogle -> checkConnection(action.activity)
        }
    }

    private fun forceSignOut() {
        viewModelScope.launch {
            when (val result = authClient.signOut()) {
                is Result.Error -> sendEvent(SignInEvent.SignInError(result.error.toUiText()))
                is Result.Success -> sendEvent(SignInEvent.SignOutSuccess)
            }
        }
    }

    private fun checkConnection(activity: Activity) {
        viewModelScope.launch {
            when (val internetStatus = connectivityObserver.internetStatus.first()) {
                InternetStatus.AVAILABLE, InternetStatus.LOSING -> signInWithGoogle(activity)
                else -> sendEvent(SignInEvent.SignInError(internetStatus.toUiText()))
            }
        }
    }

    private suspend fun signInWithGoogle(activity: Activity) {
        updateState { it.copy(isLoading = true) }

        when (val result = authClient.signIn(activity)) {
            is Result.Error -> sendEvent(SignInEvent.SignInError(result.error.toUiText()))
            is Result.Success -> sendEvent(SignInEvent.SignInSuccess)
        }

        updateState { it.copy(isLoading = false) }
    }

}
