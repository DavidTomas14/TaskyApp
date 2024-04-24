package com.davidtomas.taskyapp.features.auth.data.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModelStubs
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase

class FakeAuthServiceImpl : AuthService {
    override suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError.Network> =
        Result.Success(Unit)

    override suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError.Network> =
        Result.Success(AuthModelStubs.stub())

    override suspend fun authenticate(): Result<Unit, DataError.Network> = Result.Success(Unit)
}