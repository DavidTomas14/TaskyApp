package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

sealed class AgendaUiEvent {
    data object NavigateToLogin : AgendaUiEvent()
}