package com.revakovskyi.core.presentation.utils

import com.revakovskyi.core.domain.connectivity.InternetStatus

fun InternetStatus.toUiText(): UiText {
    return when (this) {
        InternetStatus.UNAVAILABLE, InternetStatus.LOST -> UiText.StringResource(R.string.internet_no_connection)
        InternetStatus.AVAILABLE -> UiText.StringResource(R.string.internet_restored)
        InternetStatus.LOSING -> TODO()
    }
}
