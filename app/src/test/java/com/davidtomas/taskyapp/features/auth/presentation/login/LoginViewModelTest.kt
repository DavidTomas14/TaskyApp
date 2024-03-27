package com.davidtomas.taskyapp.features.auth.presentation.login

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEqualTo
import com.davidtomas.taskyapp.MainCoroutineExtension
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModelStubs
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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
        assertThat(loginViewModel.state.isPasswordVisible).isNotEqualTo(initialPasswordVisibility)
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when login button is clicked and fields are well filled, login is performed successfully`() =
        runTest {
            loginViewModel.onAction(LoginAction.OnEmailChanged("email"))
            loginViewModel.onAction(LoginAction.OnPasswordChanged("password"))
            coEvery { authRepository.login(any()) } returns Result.Success(AuthModelStubs.stub())

            loginViewModel.uiEvent.test {
                loginViewModel.onAction(LoginAction.OnLoginButtonClick)
                val eventCollected1 = awaitItem()
                val eventCollected2 = awaitItem()
                advanceUntilIdle()

                assertThat(eventCollected1).isEqualTo(
                    LoginUiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.login_success
                        )
                    )
                )
                assertThat(eventCollected2).isEqualTo(
                    LoginUiEvent.Navigate
                )
            }
        }

    @Test
    fun `when login button is clicked and fields are incorrectly filled, login returns error`() =
        runTest {
            coEvery { authRepository.login(any()) } returns Result.Error(DataError.Network.BAD_REQUEST)
            addEmailAndPasswordAndReturnBadRequestWhenLogin(
                email = "email",
                password = "wrongPassword"
            )

            loginViewModel.uiEvent.test {
                val eventCollected: LoginUiEvent?
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
            addEmailAndPasswordAndReturnBadRequestWhenLogin(
                email = String.EMPTY_STRING,
                password = "password"
            )

            loginViewModel.uiEvent.test {
                val eventCollected: LoginUiEvent?
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
            addEmailAndPasswordAndReturnBadRequestWhenLogin(
                email = "email",
                password = String.EMPTY_STRING
            )

            loginViewModel.uiEvent.test {
                val eventCollected: LoginUiEvent?
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

    private fun addEmailAndPasswordAndReturnBadRequestWhenLogin(email: String, password: String) {
        loginViewModel.onAction(LoginAction.OnEmailChanged(email))
        loginViewModel.onAction(LoginAction.OnPasswordChanged(password))
        coEvery { authRepository.login(any()) } returns Result.Error(DataError.Network.BAD_REQUEST)
    }
}