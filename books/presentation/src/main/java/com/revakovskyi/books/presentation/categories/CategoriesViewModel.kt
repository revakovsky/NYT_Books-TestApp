package com.revakovskyi.books.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn

class CategoriesViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _event = Channel<CategoriesEvent>()
    val event: Flow<CategoriesEvent> = _event.receiveAsFlow()

    private val _state = MutableStateFlow(CategoriesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CategoriesState()
        )


    fun onAction(action: CategoriesAction) {
        when (action) {
            CategoriesAction.SignOut -> signOut()
        }
    }

    private fun signOut() {
        _event.trySend(CategoriesEvent.SignOut)
    }

}
