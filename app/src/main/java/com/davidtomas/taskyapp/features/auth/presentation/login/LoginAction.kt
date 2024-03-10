package com.davidtomas.taskyapp.features.auth.presentation.login

sealed class LoginAction {
    data class OnEmailChanged(val email: String) : LoginAction()
    data class OnPasswordChanged(val password: String) : LoginAction()
    data object OnChangePasswordVisibility : LoginAction()
    data object NavigateToRegister : LoginAction()
    data object OnLoginButtonClick : LoginAction()
}
