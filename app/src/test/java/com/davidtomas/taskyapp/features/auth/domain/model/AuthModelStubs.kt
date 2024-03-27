package com.davidtomas.taskyapp.features.auth.domain.model

object AuthModelStubs {
    private val base = AuthModel(
        token = "token",
        userId = "userId",
        fullName = "fullName"
    )

    fun stub() = base
}