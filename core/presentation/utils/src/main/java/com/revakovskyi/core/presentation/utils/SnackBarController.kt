package com.revakovskyi.core.presentation.utils

import com.revakovskyi.core.presentation.utils.snack_bar_models.SnackBarEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackBarController {

    private var _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()


    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }

}
