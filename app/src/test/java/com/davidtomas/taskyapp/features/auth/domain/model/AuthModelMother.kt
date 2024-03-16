package com.davidtomas.taskyapp.features.auth.domain.model

object AuthModelMother {
    private val base = AuthModel(
        token = "token",
        userId = "userId",
        fullName = "fullName"
    )

    fun mock() = base
}