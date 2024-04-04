package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

sealed class AgendaDetailAction {
    data object OnCloseDetailIconClick : AgendaDetailAction()
    data object OnEditIconClick : AgendaDetailAction()
    data object OnSaveClick : AgendaDetailAction()
    data object OnDeleteButtonClick : AgendaDetailAction()
    data class OnHourMinutesChanged(val millisOfHour: Long, val millisOfMinutes: Long) :
        AgendaDetailAction()

    data class OnDateChanged(val millisOfDate: Long) : AgendaDetailAction()
    data class OnNotificationOptionSelected(val millisOfNotification: Long) : AgendaDetailAction()
}