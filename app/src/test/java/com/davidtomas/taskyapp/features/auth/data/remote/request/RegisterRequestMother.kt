package com.davidtomas.taskyapp.features.auth.data.remote.request

object RegisterRequestMother {
    private val base = RegisterRequest(
        fullName = "fullName",
        email = "email",
        password = "password",
    )

    fun mock() = base
}