package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

sealed class AgendaDetailAction {
    data object OnCloseDetailIconClick : AgendaDetailAction()
    data object OnEditIconClick : AgendaDetailAction()
    data object OnSaveClick : AgendaDetailAction()
    data class OnHourChanged(val hour: Long) : AgendaDetailAction()
    data class OnDateChanged(val date: Long) : AgendaDetailAction()
}