package com.davidtomas.taskyapp.features.auth.data.remote.api

import com.davidtomas.taskyapp.core.data.remote.response.ErrorResponse
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.domain.util.Result
import com.davidtomas.taskyapp.features.auth.data.remote.mapper.toAuthModel
import com.davidtomas.taskyapp.features.auth.data.remote.mapper.toLoginRequest
import com.davidtomas.taskyapp.features.auth.data.remote.mapper.toRegisterRequest
import com.davidtomas.taskyapp.features.auth.data.remote.response.LoginResponse
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

@Suppress("SwallowedException", "TooGenericExceptionCaught")
class AuthServiceImpl(
    private val client: HttpClient,
) : AuthService {
    override suspend fun register(registerParams: RegisterUseCase.RegisterParams): Result<Unit, DataError> {
        return try {
            client.post(AuthRoutes.REGISTER_ROUTE) {
                method = HttpMethod.Post
                setBody(registerParams.toRegisterRequest())
            }
            Result.Success(Unit)
        } catch (e: ClientRequestException) {
            Result.Error(
                DataError.CustomNetworkError(
                    e.response.body<ErrorResponse>().message
                )
            )
        } catch (e: ServerResponseException) {
            Result.Error(
                DataError.CustomNetworkError(
                    e.response.body<ErrorResponse>().message
                )
            )
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }

    override suspend fun login(loginParams: LoginUseCase.LoginParams): Result<AuthModel, DataError> {
        return try {
            val response = client
                .request(AuthRoutes.LOGIN_ROUTE) {
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(loginParams.toLoginRequest())
                }
            Result.Success(response.body<LoginResponse>().toAuthModel())
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        } catch (e: ClientRequestException) {
            Result.Error(
                when (e.response.status.value) {
                    401 -> DataError.Network.UNAUTHORIZED
                    409 -> DataError.CustomNetworkError(e.response.body<ErrorResponse>().message)
                    else -> DataError.Network.UNKNOWN
                }
            )
        } catch (e: ServerResponseException) {
            Result.Error(
                DataError.CustomNetworkError(
                    e.response.body<ErrorResponse>().message
                )
            )
        }
    }

    override suspend fun authenticate(): Result<Unit, DataError> {
        return try {
            val response = client.request(AuthRoutes.AUTHENTICATE_ROUTE) {
                method = HttpMethod.Get
            }
            Result.Success(response.body())
        } catch (e: ClientRequestException) {
            Result.Error(
                when (e.response.status.value) {
                    401 -> DataError.Network.UNAUTHORIZED
                    else -> DataError.Network.UNKNOWN
                }
            )
        } catch (e: ServerResponseException) {
            Result.Error(
                DataError.CustomNetworkError(
                    e.response.body<ErrorResponse>().message
                )
            )
        } catch (e: IOException) {
            Result.Error(DataError.Network.NO_INTERNET)
        } catch (e: SerializationException) {
            Result.Error(DataError.Network.SERIALIZATION)
        }
    }
}