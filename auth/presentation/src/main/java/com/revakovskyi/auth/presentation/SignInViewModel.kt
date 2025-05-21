package com.revakovskyi.auth.presentation

import android.app.Activity
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.auth.presentation.auth_client.AuthClient
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.connectivity.InternetStatus
import com.revakovskyi.core.domain.util.Result
import com.revakovskyi.core.presentation.utils.text_converters.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    savedStateHandle: SavedStateHandle,
    private val connectivityObserver: ConnectivityObserver,
    private val authClient: AuthClient,
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    private val _event = Channel<SignInEvent>()
    val event: Flow<SignInEvent> = _event.receiveAsFlow()


    init {
        val forceSignOut = savedStateHandle["forceSignOut"] ?: false
        if (forceSignOut) forceSignOut()
    }


    fun onAction(action: SignInAction) {
        when (action) {
            is SignInAction.SignInWithGoogle -> checkConnection(action.activity)
        }
    }

    private fun forceSignOut() {
        viewModelScope.launch {
            when (val result = authClient.signOut()) {
                is Result.Error -> _event.send(SignInEvent.SignInError(result.error.toUiText()))
                is Result.Success -> _event.send(SignInEvent.SignOutSuccess)
            }
        }
    }

    private fun checkConnection(activity: Activity) {
        viewModelScope.launch {
            when (val internetStatus = connectivityObserver.internetStatus.first()) {
                InternetStatus.AVAILABLE, InternetStatus.LOSING -> signInWithGoogle(activity)
                else -> _event.send(SignInEvent.SignInError(internetStatus.toUiText()))
            }
        }
    }

    private suspend fun signInWithGoogle(activity: Activity) {
        _state.update { it.copy(isLoading = true) }

        when (val result = authClient.signIn(activity)) {
            is Result.Error -> _event.send(SignInEvent.SignInError(result.error.toUiText()))
            is Result.Success -> _event.send(SignInEvent.SignInSuccess)
        }

        _state.update { it.copy(isLoading = false) }
    }

}
