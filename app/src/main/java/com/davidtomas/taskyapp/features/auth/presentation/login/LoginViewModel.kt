package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

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

            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            loginUseCase(
                LoginUseCase.LoginParams(
                    email = state.email,
                    password = state.password
                )
            ).fold(
                onError = {
                    println(it)
                },
                onSuccess = {
                    println(it)
                }
            )
        }
    }
}