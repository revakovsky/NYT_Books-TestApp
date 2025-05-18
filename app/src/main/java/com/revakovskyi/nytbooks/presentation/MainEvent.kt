package com.revakovskyi.nytbooks.presentation

sealed interface MainEvent {

    data object ShowInternetNotification : MainEvent

}
