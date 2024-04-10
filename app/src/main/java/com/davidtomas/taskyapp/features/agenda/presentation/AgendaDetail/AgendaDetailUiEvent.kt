package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import com.davidtomas.taskyapp.core.presentation.util.UiText

sealed class AgendaDetailUiEvent {
    data object NavigateUp : AgendaDetailUiEvent()
    data class ShowSnackBar(val message: UiText) : AgendaDetailUiEvent()
}