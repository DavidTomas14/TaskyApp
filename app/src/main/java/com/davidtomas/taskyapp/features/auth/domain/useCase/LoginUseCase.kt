package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.domain.util.Result
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel

class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(loginParams: LoginParams): Result<AuthModel, DataError.Network> {
        return authRepository.login(loginParams)
    }

    data class LoginParams(
        val email: String,
        val password: String
    )
}