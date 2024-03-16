package com.davidtomas.taskyapp.features.auth.data.remote.api

import assertk.assertThat
import assertk.assertions.isTrue
import com.davidtomas.taskyapp.core.domain._util.isError
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AuthServiceImplTest {

    private lateinit var authService: AuthService

    private lateinit var client: HttpClient

    @BeforeEach
    fun setUp() {
        client = mockk(relaxed = true)
        authService = AuthServiceImpl(
            client = client
        )
    }

    @Test
    fun `When there is a ClientRequestException while register, a BAD_REQUEST is returned`() =
        runTest {
            coEvery {
                client.post(
                    any<String>(),
                    any<HttpRequestBuilder.() -> Unit>()
                )
            } throws mockk<ClientRequestException>()
            val result = authService.register(
                RegisterUseCase.RegisterParams(
                    fullName = "fullName",
                    email = "email",
                    password = "password"
                )
            )
            assertThat(result.isError()).isTrue()
        }
}