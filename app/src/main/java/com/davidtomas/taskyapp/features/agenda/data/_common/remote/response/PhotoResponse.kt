package com.davidtomas.taskyapp.features.agenda.data._common.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    val key: String,
    val url: String
)