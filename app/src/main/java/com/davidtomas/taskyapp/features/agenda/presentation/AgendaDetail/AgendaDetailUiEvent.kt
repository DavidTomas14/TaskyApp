package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

sealed class AgendaDetailUiEvent {
    data object NavigateUp : AgendaDetailUiEvent()
    data object NavigateToEditTitle : AgendaDetailUiEvent()
    data object NavigateToEditDescription : AgendaDetailUiEvent()
}