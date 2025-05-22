package com.revakovskyi.core.presentation.utils.text_converters

import com.revakovskyi.core.domain.connectivity.InternetStatus
import com.revakovskyi.core.presentation.utils.R
import com.revakovskyi.core.presentation.utils.UiText

fun InternetStatus.toUiText(): UiText {
    return when (this) {
        InternetStatus.UNAVAILABLE, InternetStatus.LOST -> UiText.StringResource(R.string.internet_no_connection)
        InternetStatus.AVAILABLE -> UiText.StringResource(R.string.internet_restored)
        InternetStatus.LOSING -> UiText.DynamicString("")
    }
}
