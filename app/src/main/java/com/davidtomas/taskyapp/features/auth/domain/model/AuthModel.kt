package com.davidtomas.taskyapp.features.auth.domain.model

data class AuthModel(
    val token: String,
    val userId: String,
    val fullName: String
)
