package com.davidtomas.taskyapp.features.auth.data.remote

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.map
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.data.local.TokenDataStore
import com.davidtomas.taskyapp.features.auth.data.remote.api.AuthService
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val tokenDataStore: TokenDataStore
) : AuthRepository {
    override suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError.Network> {
        return when (val result = authService.login(loginParams)) {
            is Result.Error -> result
            is Result.Success -> {
                result.map {
                    tokenDataStore.saveToken(it.token)
                }
                result
            }
        }
    }

    override suspend fun logout(): Result<Unit, DataError.Network> {
        return authService.logout()
    }

    override suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError.Network> {
        return authService.register(registerParams)
    }

    override suspend fun authenticate(): Result<Unit, DataError> {
        return authService.authenticate()
    }
}