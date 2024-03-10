package com.davidtomas.taskyapp.features.auth.presentation.register

import com.davidtomas.taskyapp.core.presentation.util.UiText

sealed class RegisterUiEvent {
    data class ShowSnackBar(val message: UiText) : RegisterUiEvent()
}