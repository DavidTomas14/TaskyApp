package com.davidtomas.taskyapp.features.auth.presentation._common.mapper

import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain.model.DataError

fun DataError.Network?.toStringResource() =
    when (this) {
        DataError.Network.UNAUTHORIZED -> R.string.error_unauthorized
        DataError.Network.BAD_REQUEST -> R.string.error_bad_request
        DataError.Network.NO_INTERNET -> R.string.error_no_internet
        DataError.Network.SERVER_ERROR -> R.string.error_server
        DataError.Network.SERIALIZATION -> R.string.error_serialization
        else -> R.string.error_unknown
    }