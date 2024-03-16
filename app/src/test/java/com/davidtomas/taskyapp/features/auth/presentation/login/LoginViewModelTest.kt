package com.davidtomas.taskyapp.features.auth.presentation.login

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.davidtomas.taskyapp.MainCoroutineExtension
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModelMother
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var authRepository: AuthRepository

    @BeforeEach
    fun setUp() {
        authRepository = mockk()
        loginUseCase = LoginUseCase(
            authRepository = authRepository
        )
        loginViewModel = LoginViewModel(
            loginUseCase = loginUseCase
        )
    }

    @Test
    fun `when onAction receives OnChangePasswordVisibility action password visibility is changed`() {
        val initialPasswordVisibility = loginViewModel.state.isPasswordVisible
        loginViewModel.onAction(LoginAction.OnChangePasswordVisibility)
        assertTrue(loginViewModel.state.isPasswordVisible != initialPasswordVisibility)
    }

    @Test
    fun `when onAction receives OnEmailChanged action email is changed`() {
        val newEmail = "newEmail"
        loginViewModel.onAction(LoginAction.OnEmailChanged(newEmail))
        assertThat(loginViewModel.state.email).isEqualTo(newEmail)
    }

    @Test
    fun `when onAction receives OnPasswordChanged action password is changed`() {
        val newPassword = "password"
        loginViewModel.onAction(LoginAction.OnPasswordChanged(newPassword))
        assertThat(loginViewModel.state.password).isEqualTo(newPassword)
    }

    @Test
    fun `when login button is clicked and fields are well filled, login is performed successfully`() =
        runTest {
            var eventCollected: LoginUiEvent?
            val newEmail = "newEmail"
            val newPassword = "password"
            coEvery { authRepository.login(any()) } returns Result.Success(AuthModelMother.mock())
            loginViewModel.onAction(LoginAction.OnEmailChanged(newEmail))
            loginViewModel.onAction(LoginAction.OnPasswordChanged(newPassword))

            loginViewModel.uiEvent.test {
                loginViewModel.onAction(LoginAction.OnLoginButtonClick)
                eventCollected = awaitItem()
                assertThat(eventCollected).isEqualTo(
                    LoginUiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.login_success
                        )
                    )
                )
            }
        }

    @Test
    fun `when login button is clicked and fields are incorrectly filled, login returns error`() =
        runTest {
            var eventCollected: LoginUiEvent?
            val newEmail = "newEmail"
            val newPassword = "wrongPassword"
            coEvery { authRepository.login(any()) } returns Result.Error(DataError.Network.BAD_REQUEST)
            loginViewModel.onAction(LoginAction.OnEmailChanged(newEmail))
            loginViewModel.onAction(LoginAction.OnPasswordChanged(newPassword))

            loginViewModel.uiEvent.test {
                loginViewModel.onAction(LoginAction.OnLoginButtonClick)
                eventCollected = awaitItem()
                assertThat(eventCollected).isEqualTo(
                    LoginUiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.error_bad_request
                        )
                    )
                )
            }
        }

    @Test
    fun `when login button is clicked and email is empty, informative snackbar is shown`() =
        runTest {
            var eventCollected: LoginUiEvent?
            val newEmail = String.EMPTY_STRING
            val newPassword = "wrongPassword"
            coEvery { authRepository.login(any()) } returns Result.Error(DataError.Network.BAD_REQUEST)
            loginViewModel.onAction(LoginAction.OnEmailChanged(newEmail))
            loginViewModel.onAction(LoginAction.OnPasswordChanged(newPassword))

            loginViewModel.uiEvent.test {
                loginViewModel.onAction(LoginAction.OnLoginButtonClick)
                eventCollected = awaitItem()
                assertThat(eventCollected).isEqualTo(
                    LoginUiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.error_fields_must_be_filled
                        )
                    )
                )
            }
        }

    @Test
    fun `when login button is clicked and password is empty, informative snackbar is shown`() =
        runTest {
            var eventCollected1: LoginUiEvent?
            var eventCollected2: LoginUiEvent?
            val newEmail = "newEmail"
            val newPassword = String.EMPTY_STRING
            coEvery { authRepository.login(any()) } returns Result.Error(DataError.Network.BAD_REQUEST)
            loginViewModel.onAction(LoginAction.OnEmailChanged(newEmail))
            loginViewModel.onAction(LoginAction.OnPasswordChanged(newPassword))

            loginViewModel.uiEvent.test {
                loginViewModel.onAction(LoginAction.OnLoginButtonClick)
                eventCollected1 = awaitItem()
                eventCollected2 = awaitItem()
                awaitComplete()

                assertThat(eventCollected1).isEqualTo(
                    LoginUiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.error_fields_must_be_filled
                        )
                    )
                )
                assertThat(eventCollected2).isEqualTo(
                    LoginUiEvent.Navigate
                )
            }
        }
}