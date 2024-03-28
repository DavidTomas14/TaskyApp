package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository

class AuthenticateUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit, DataError> {
        return authRepository.authenticate()
    }
}