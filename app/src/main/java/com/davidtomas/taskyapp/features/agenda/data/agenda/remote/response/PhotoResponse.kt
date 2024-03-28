package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(
    val key: String,
    val url: String
)