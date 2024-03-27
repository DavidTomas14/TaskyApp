package com.davidtomas.taskyapp.features.auth.data.remote

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.isSuccess
import com.davidtomas.taskyapp.features.auth.data.local.FakeTokenDataStoreImpl
import com.davidtomas.taskyapp.features.auth.data.local.TokenDataStore
import com.davidtomas.taskyapp.features.auth.data.remote.api.AuthService
import com.davidtomas.taskyapp.features.auth.data.remote.api.FakeAuthServiceImpl
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class AuthRepositoryImplTest {

    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var tokenDataStore: TokenDataStore
    private lateinit var authService: AuthService

    @BeforeEach
    fun setUp() {
        tokenDataStore = FakeTokenDataStoreImpl()
        authService = FakeAuthServiceImpl()
        authRepository = AuthRepositoryImpl(
            authService = authService,
            tokenDataStore = tokenDataStore
        )
    }

    @Test
    fun `when login is successfully done, token should be saved in dataStore and should return Success`() =
        runTest {
            val result = authRepository.login(
                LoginUseCase.LoginParams(
                    email = "email",
                    password = "password"
                )
            )
            assertTrue { result.isSuccess() }
            assertThat(
                tokenDataStore.getToken().first()
            ).isEqualTo((result as Result.Success).data.token)
        }

    @Test
    fun `when register is successfully done, should return Success`() =
        runTest {
            val result = authRepository.register(
                RegisterUseCase.RegisterParams(
                    fullName = "fullName",
                    email = "email",
                    password = "password"
                )
            )
            assertTrue { result.isSuccess() }
        }

    @Test
    fun `when authenticate is successfully done, should return Success`() =
        runTest {
            val result = authRepository.authenticate()
            assertTrue { result.isSuccess() }
        }
}