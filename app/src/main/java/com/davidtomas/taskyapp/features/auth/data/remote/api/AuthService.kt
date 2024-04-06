package com.davidtomas.taskyapp.features.auth.data.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase

interface AuthService {

    suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError.Network>

    suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError.Network>
    suspend fun authenticate(): Result<Unit, DataError.Network>
    suspend fun logout(): Result<Unit, DataError.Network>
}