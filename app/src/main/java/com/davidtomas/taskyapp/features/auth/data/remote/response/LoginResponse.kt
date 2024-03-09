package com.davidtomas.taskyapp.features.auth.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val userId: String,
    val fullName: String
)
