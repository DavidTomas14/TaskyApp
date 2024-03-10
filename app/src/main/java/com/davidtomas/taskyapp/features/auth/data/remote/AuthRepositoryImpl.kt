package com.davidtomas.taskyapp.features.auth.data.remote

import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.domain.util.Result
import com.davidtomas.taskyapp.core.domain.util.map
import com.davidtomas.taskyapp.features.auth.data.local.TokenManager
import com.davidtomas.taskyapp.features.auth.data.remote.api.AuthService
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError> {
        return when (val result = authService.login(loginParams)) {
            is Result.Error -> result
            is Result.Success -> {
                result.map {
                    tokenManager.saveToken(it.token)
                }
                result
            }
        }
    }

    override suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError> {
        return authService.register(registerParams)
    }

    override suspend fun authenticate(): Result<Unit, DataError> {
        return authService.authenticate()
    }
}