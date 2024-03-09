package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.domain.util.Result
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(registerParams: RegisterParams): Result<Unit, DataError> {
        return authRepository.register(registerParams)
    }

    data class RegisterParams(
        val fullName: String,
        val email: String,
        val password: String
    )
}