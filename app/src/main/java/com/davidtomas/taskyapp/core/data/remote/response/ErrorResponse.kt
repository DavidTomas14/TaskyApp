package com.davidtomas.taskyapp.core.data.remote.response

import com.davidtomas.taskyapp.core.domain._util.Error
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String
) : Error
