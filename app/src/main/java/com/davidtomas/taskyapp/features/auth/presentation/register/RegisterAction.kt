package com.davidtomas.taskyapp.features.auth.presentation.register

sealed class RegisterAction
data class OnUserNameChanged(val userName: String) : RegisterAction()
data class OnEmailChanged(val email: String) : RegisterAction()
data class OnPasswordChanged(val password: String) : RegisterAction()
data object OnChangePasswordVisibility : RegisterAction()
data object NavigateBackToLogin : RegisterAction()
