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
    data object OnNavigateToEditTitleClick : AgendaDetailAction()
    data object OnNavigateToEditDescriptionClick : AgendaDetailAction()
    data class OnTitleChanged(val title: String) : AgendaDetailAction()
    data class OnDescriptionChanged(val description: String) : AgendaDetailAction()
    data class OnAddedPhoto(val photoUri: String) : AgendaDetailAction()
    data class OnPhotoClicked(val photoUri: String) : AgendaDetailAction()
}