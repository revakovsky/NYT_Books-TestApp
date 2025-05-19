package com.revakovskyi.nytbooks.presentation

import com.revakovskyi.core.presentation.utils.UiText

sealed interface MainEvent {

    data class ShowInternetStatus(
        val message: UiText,
        val internetAvailable: Boolean,
    ) : MainEvent

}
