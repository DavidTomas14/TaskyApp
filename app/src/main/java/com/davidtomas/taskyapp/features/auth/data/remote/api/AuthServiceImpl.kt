package com.davidtomas.taskyapp.features.auth.data.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.map
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.data.remote.mapper.toAuthModel
import com.davidtomas.taskyapp.features.auth.data.remote.mapper.toLoginRequest
import com.davidtomas.taskyapp.features.auth.data.remote.mapper.toRegisterRequest
import com.davidtomas.taskyapp.features.auth.data.remote.response.LoginResponse
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class AuthServiceImpl(
    val client: HttpClient,
) : AuthService {
    override suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AuthRoutes.REGISTER_ROUTE) }
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(registerParams.toRegisterRequest())
        }

    override suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError.Network> =
        client.safeRequest<LoginResponse> {
            url { path(AuthRoutes.LOGIN_ROUTE) }
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(loginParams.toLoginRequest())
        }.map {
            it.toAuthModel()
        }

    override suspend fun authenticate(): Result<Unit, DataError> =
        client.safeRequest<Unit> {
            url { path(AuthRoutes.AUTHENTICATE_ROUTE) }
            method = HttpMethod.Get
        }
}
