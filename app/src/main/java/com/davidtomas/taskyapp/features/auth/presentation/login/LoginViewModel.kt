package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

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
}