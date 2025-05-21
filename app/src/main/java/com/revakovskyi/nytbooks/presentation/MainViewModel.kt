package com.revakovskyi.nytbooks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.auth.presentation.auth_client.AuthClient
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.connectivity.InternetStatus
import com.revakovskyi.core.presentation.utils.text_converters.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val connectivityObserver: ConnectivityObserver,
    private val authClient: AuthClient,
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state
        .onStart {
            if (!hasLoadedInitialData) {
                val isSignedIn = authClient.isSignedIn()
                _state.update { it.copy(isSignedIn = isSignedIn) }

                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = MainState()
        )

    private val _event = Channel<MainEvent>()
    val event: Flow<MainEvent> = _event.receiveAsFlow()

    private var firstLaunch = true


    init {
        observeInternetStatus()
    }


    private fun observeInternetStatus() {
        connectivityObserver.internetStatus
            .dropWhile { firstLaunch && it == InternetStatus.AVAILABLE }
            .onEach { status ->
                firstLaunch = false

                when (status) {
                    InternetStatus.UNAVAILABLE, InternetStatus.LOST -> {
                        _event.send(
                            MainEvent.ShowInternetStatus(
                                message = status.toUiText(),
                                internetAvailable = false
                            )
                        )
                    }

                    InternetStatus.AVAILABLE -> {
                        _event.send(
                            MainEvent.ShowInternetStatus(
                                message = status.toUiText(),
                                internetAvailable = true
                            )
                        )
                    }

                    InternetStatus.LOSING -> Unit
                }
            }.launchIn(viewModelScope)
    }

}