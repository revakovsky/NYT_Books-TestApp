package com.revakovskyi.core.domain.util

sealed interface DataError : Error {

    enum class Network : DataError {
        UNAUTHORIZED,
        FORBIDDEN,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        SERVER_ERROR,
        NO_INTERNET,
        SERIALIZATION,
        UNKNOWN,
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN,
    }

}