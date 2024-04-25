package com.davidtomas.taskyapp.features.auth.presentation.register

sealed class RegisterUiEvent {
    data object NavigateUp : RegisterUiEvent()
}