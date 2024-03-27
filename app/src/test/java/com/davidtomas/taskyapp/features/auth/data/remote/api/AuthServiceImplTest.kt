package com.davidtomas.taskyapp.features.auth.data.remote.api

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isTrue
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.isError
import com.davidtomas.taskyapp.core.domain._util.isSuccess
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.auth.data.remote.toDomainError
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import com.davidtomas.taskyapp.util.AddLogExtension
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.koin.test.KoinTest

@ExtendWith(AddLogExtension::class)
internal class AuthServiceImplTest : KoinTest {

    private lateinit var authService: AuthServiceImpl

    @Test
    fun `Login is executed with valid data, it returns success response`() =
        runTest {
            authService = spyk(AuthServiceImpl(client = ktorSuccessClient))
            val result = authService.login(
                LoginUseCase.LoginParams(
                    email = "email",
                    password = "password"
                )
            )
            assertThat((result as Result.Success).data.token).isEqualTo("token")
            assertThat(result.isSuccess()).isTrue()
        }

    @ParameterizedTest
    @MethodSource("httpStatusCodes")
    fun `When an error occurs in login, it returns DataError Network`(statusCode: HttpStatusCode) {
        runTest {
            authService =
                spyk(AuthServiceImpl(client = ktorErrorClientWithSpecificError(statusCode)))
            val result = authService.login(
                LoginUseCase.LoginParams(
                    email = "email",
                    password = ""
                )
            )
            val dataError = statusCode.toDomainError()
            assertThat((result as Result.Error).error).isEqualTo(dataError)
            assertThat(result.isError()).isTrue()
        }
    }

    @ParameterizedTest
    @MethodSource("exceptions")
    fun `When there is an exception during api call, such error is returned`(exception: Exception) {
        runTest {
            authService =
                spyk(AuthServiceImpl(client = ktorExceptionErrorClient(exception)))
            val result = authService.login(
                LoginUseCase.LoginParams(
                    email = "email",
                    password = ""
                )
            )

            assertThat((result as Result.Error).error).isEqualTo(
                when (exception) {
                    is IOException -> DataError.Network.NO_INTERNET
                    is SerializationException -> DataError.Network.SERIALIZATION
                    else -> Unit
                }
            )
            assertThat(result.isError()).isTrue()
        }
    }

    @Test
    fun `Register is executed with valid data, it returns success response`() =
        runTest {
            authService = spyk(AuthServiceImpl(client = ktorSuccessClient))
            val result = authService.register(
                RegisterUseCase.RegisterParams(
                    fullName = "fullName",
                    email = "email",
                    password = "password"
                )
            )
            assertThat(result).isInstanceOf(Result.Success::class)
        }

    @Test
    fun `Authenticate is executed with valid data, it returns success response`() =
        runTest {
            authService = spyk(AuthServiceImpl(client = ktorSuccessClient))
            val result = authService.authenticate()
            assertThat(result).isInstanceOf(Result.Success::class)
        }

    companion object {
        @JvmStatic
        fun httpStatusCodes(): List<HttpStatusCode> {
            return listOf(
                HttpStatusCode.Conflict,
                HttpStatusCode.NotFound,
                HttpStatusCode.InternalServerError,
                HttpStatusCode.Unauthorized
            )
        }

        @JvmStatic
        fun exceptions(): List<Exception> {
            return listOf(
                IOException(),
                SerializationException()
            )
        }
    }
}