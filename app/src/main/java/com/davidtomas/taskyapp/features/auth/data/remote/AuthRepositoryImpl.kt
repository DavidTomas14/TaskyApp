package com.davidtomas.taskyapp.features.auth.data.remote

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.map
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStore
import com.davidtomas.taskyapp.features.auth.data.remote.api.AuthService
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import kotlinx.coroutines.flow.first

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val taskyDataStore: TaskyDataStore
) : AuthRepository {
    override suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError.Network> {
        return when (val result = authService.login(loginParams)) {
            is Result.Error -> result
            is Result.Success -> {
                result.map {
                    taskyDataStore.saveToken(it.token)
                    taskyDataStore.saveFullName(it.fullName)
                    taskyDataStore.saveUserId(it.userId)
                }
                result
            }
        }
    }

    override suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError.Network> {
        return authService.register(registerParams)
    }

    override suspend fun authenticate(): Result<Unit, DataError> {
        authService.authenticate().fold(
            onError = {
                return if (it == DataError.Network.NO_INTERNET) checkIfHasToken()
                else Result.Error(it)
            },
            onSuccess = {
                return Result.Success(Unit)
            }
        )
        return Result.Success(Unit)
    }

    private suspend fun checkIfHasToken(): Result<Unit, DataError.Network> =
        if (!taskyDataStore.getToken().first().isNullOrBlank()) {
            Result.Success(Unit)
        } else {
            Result.Error(DataError.Network.NO_INTERNET)
        }
}