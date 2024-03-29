package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.presentation._common.mapper.toStringResource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    private val _uiEvent = Channel<LoginUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnChangePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is LoginAction.OnEmailChanged -> {
                state = state.copy(email = action.email)
            }

            is LoginAction.OnPasswordChanged -> {
                state = state.copy(password = action.password)
            }

            is LoginAction.OnLoginButtonClick -> {
                if (areFieldsFilled()) {
                    login()
                } else {
                    viewModelScope.launch {
                        _uiEvent.send(
                            LoginUiEvent.ShowSnackBar(
                                UiText.StringResource(
                                    resId = R.string.error_fields_must_be_filled
                                )
                            )
                        )
                    }
                }
            }

            else -> Unit
        }
    }

    private fun areFieldsFilled() = state.email.isNotBlank() && state.password.isNotBlank()

    private fun login() {
        viewModelScope.launch {
            loginUseCase(
                LoginUseCase.LoginParams(
                    email = state.email,
                    password = state.password
                )
            ).fold(
                onError = { dataError ->
                    _uiEvent.send(
                        LoginUiEvent.ShowSnackBar(
                            UiText.StringResource(
                                resId = dataError.toStringResource()
                            )
                        )
                    )
                },
                onSuccess = {
                    _uiEvent.send(
                        LoginUiEvent.ShowSnackBar(
                            UiText.StringResource(
                                R.string.login_success
                            )
                        )
                    )
                    _uiEvent.send(LoginUiEvent.Navigate)
                }
            )
        }
    }
}