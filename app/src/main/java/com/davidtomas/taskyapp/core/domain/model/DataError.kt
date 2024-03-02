package com.davidtomas.taskyapp.core.domain.model

sealed interface DataError : Failure
enum class Network : DataError {
    REQUEST_TIMEOUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN
}

enum class Local : DataError {
    DISK_FULL
}
