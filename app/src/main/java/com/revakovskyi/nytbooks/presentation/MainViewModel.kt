package com.revakovskyi.nytbooks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
//    private val connectivityObserver: ConnectivityObserver,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val _event = Channel<MainEvent>()
    val event: Flow<MainEvent> = _event.receiveAsFlow()


    init {
        observeInitialData()
    }


    private fun observeInitialData() {
        // TODO: update initial data and remove a code below!
        viewModelScope.launch {
            delay(2000)

            _event.send(MainEvent.ShowInternetNotification)
            _state.update { it.copy(isSignedIn = true) }
        }
    }

}