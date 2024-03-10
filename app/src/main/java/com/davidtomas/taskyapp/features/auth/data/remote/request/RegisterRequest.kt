package com.davidtomas.taskyapp.features.auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)