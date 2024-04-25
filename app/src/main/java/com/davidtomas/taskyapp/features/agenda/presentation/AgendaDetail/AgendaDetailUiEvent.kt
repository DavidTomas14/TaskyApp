package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

sealed class AgendaDetailUiEvent {
    data object NavigateUp : AgendaDetailUiEvent()
    data class NavigateToPhotoDetail(val photoKey: String) : AgendaDetailUiEvent()
}