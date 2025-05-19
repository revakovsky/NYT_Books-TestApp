package com.revakovskyi.nytbooks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revakovskyi.core.domain.connectivity.ConnectivityObserver
import com.revakovskyi.core.domain.connectivity.InternetStatus
import com.revakovskyi.core.presentation.utils.toUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val _event = Channel<MainEvent>()
    val event: Flow<MainEvent> = _event.receiveAsFlow()

    private var firstLaunch = true


    init {
        observeInternetStatus()
        observeUserSignedIn()
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


    private fun observeUserSignedIn() {
        // TODO: update initial data and remove a code below!
        viewModelScope.launch {
            delay(2000)
            _state.update { it.copy(isSignedIn = true) }
        }
    }

}