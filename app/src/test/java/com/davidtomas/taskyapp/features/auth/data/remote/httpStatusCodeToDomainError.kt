package com.davidtomas.taskyapp.features.auth.data.remote

import com.davidtomas.taskyapp.core.domain.model.DataError
import io.ktor.http.HttpStatusCode

fun HttpStatusCode.toDomainError() =
    when (this) {
        HttpStatusCode.Conflict -> DataError.Network.BAD_REQUEST
        HttpStatusCode.InternalServerError -> DataError.Network.SERVER_ERROR
        HttpStatusCode.Unauthorized -> DataError.Network.UNAUTHORIZED
        else -> DataError.Network.UNKNOWN
    }