package com.revakovskyi.core.domain.auth

import com.revakovskyi.core.domain.util.Error

sealed interface AuthError : Error {

    enum class Google : AuthError {
        NO_GOOGLE_ACCOUNT,
        CREDENTIAL_FETCH_FAILED,
        UNSUPPORTED_CREDENTIAL_TYPE,
        CREDENTIAL_CLEAR_FAILED,
    }

    enum class Firebase : AuthError {
        INVALID_USER,
        INVALID_CREDENTIALS,
        ACCOUNT_COLLISION,
        UNKNOWN,
    }

}
