package com.davidtomas.taskyapp.features.auth.presentation.login

import com.davidtomas.taskyapp.core.presentation.util.UiText

sealed class LoginUiEvent {
    data class ShowSnackBar(val message: UiText) : LoginUiEvent()
    data object Navigate : LoginUiEvent()
}