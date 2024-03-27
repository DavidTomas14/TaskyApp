package com.davidtomas.taskyapp.features.auth.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String? = null,
    val userId: String? = null,
    val fullName: String? = null
)
