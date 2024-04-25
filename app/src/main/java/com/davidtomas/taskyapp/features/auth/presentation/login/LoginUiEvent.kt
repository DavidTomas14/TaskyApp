package com.davidtomas.taskyapp.features.auth.presentation.login

sealed class LoginUiEvent {
    data object Navigate : LoginUiEvent()
}