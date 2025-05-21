package com.revakovskyi.core.presentation.utils.text_converters

import com.revakovskyi.core.domain.auth.AuthError
import com.revakovskyi.core.presentation.utils.R
import com.revakovskyi.core.presentation.utils.UiText

fun AuthError.toUiText(): UiText {
    return when (this) {
        AuthError.Google.NO_GOOGLE_ACCOUNT -> UiText.StringResource(R.string.auth_no_google_account)
        AuthError.Google.CREDENTIAL_FETCH_FAILED -> UiText.StringResource(R.string.auth_credential_fetch_failed)
        AuthError.Google.UNSUPPORTED_CREDENTIAL_TYPE -> UiText.StringResource(R.string.auth_unsupported_credential_type)
        AuthError.Google.CREDENTIAL_CLEAR_FAILED -> UiText.StringResource(R.string.auth_credential_clear_failed)

        AuthError.Firebase.INVALID_USER -> UiText.StringResource(R.string.auth_invalid_user)
        AuthError.Firebase.INVALID_CREDENTIALS -> UiText.StringResource(R.string.auth_invalid_credentials)
        AuthError.Firebase.ACCOUNT_COLLISION -> UiText.StringResource(R.string.auth_account_collision)
        AuthError.Firebase.UNKNOWN -> UiText.StringResource(R.string.auth_unknown_error)
    }
}
