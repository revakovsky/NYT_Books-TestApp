package com.revakovskyi.nytbooks.presentation

import androidx.lifecycle.viewModelScope
import com.revakovskyi.auth.presentation.auth_client.AuthClient
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.connectivity.InternetStatus
import com.revakovskyi.core.presentation.utils.base_viewmodel.BaseViewModel
import com.revakovskyi.core.presentation.utils.text_converters.toUiText
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val connectivityObserver: ConnectivityObserver,
    private val authClient: AuthClient,
) : BaseViewModel<MainState, Unit, MainEvent>(MainState()) {

    private var firstLaunch = true


    init {
        checkUserSignedIn()
        observeInternetStatus()
    }


    private fun checkUserSignedIn() {
        val isSignedIn = authClient.isSignedIn()
        updateState { it.copy(isSignedIn = isSignedIn) }
    }

    private fun observeInternetStatus() {
        connectivityObserver.internetStatus
            .dropWhile { firstLaunch && it == InternetStatus.AVAILABLE }
            .onEach { status ->
                firstLaunch = false
                handleInternetStatus(status)
            }.launchIn(viewModelScope)
    }

    private fun handleInternetStatus(status: InternetStatus) {
        when (status) {
            InternetStatus.UNAVAILABLE, InternetStatus.LOST -> {
                sendEvent(
                    MainEvent.ShowInternetStatus(
                        message = status.toUiText(),
                        internetAvailable = false
                    )
                )
            }

            InternetStatus.AVAILABLE -> {
                sendEvent(
                    MainEvent.ShowInternetStatus(
                        message = status.toUiText(),
                        internetAvailable = true
                    )
                )
            }

            InternetStatus.LOSING -> Unit
        }
    }

}