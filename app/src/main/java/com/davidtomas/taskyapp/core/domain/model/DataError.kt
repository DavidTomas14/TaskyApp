package com.davidtomas.taskyapp.core.domain.model

import com.davidtomas.taskyapp.core.domain.util.Error

sealed interface DataError : Error {
    enum class Network : DataError {
        UNAUTHORIZED,
        NO_INTERNET,
        SERVER_ERROR,
        CANCELLATION,
        SERIALIZATION,
        BAD_REQUEST,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL
    }
}