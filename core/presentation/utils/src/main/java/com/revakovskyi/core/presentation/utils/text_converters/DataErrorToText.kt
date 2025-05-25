package com.revakovskyi.core.presentation.utils.text_converters

import com.revakovskyi.core.domain.utils.DataError
import com.revakovskyi.core.presentation.utils.R
import com.revakovskyi.core.presentation.utils.UiText

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Local.UNKNOWN -> UiText.StringResource(R.string.error_db_unknown)

        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.error_unauthorized)
        DataError.Network.FORBIDDEN -> UiText.StringResource(R.string.error_forbidden)
        DataError.Network.NOT_FOUND -> UiText.StringResource(R.string.error_not_found)
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server_error)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
    }
}
