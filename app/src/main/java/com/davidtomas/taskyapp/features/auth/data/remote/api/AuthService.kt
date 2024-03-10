package com.davidtomas.taskyapp.features.auth.data.remote.api

import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.domain.util.Result
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase

interface AuthService {

    suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError>

    suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError>
    suspend fun authenticate(): Result<Unit, DataError>
}