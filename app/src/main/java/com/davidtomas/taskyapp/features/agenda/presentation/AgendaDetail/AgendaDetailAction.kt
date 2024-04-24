package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel

sealed class AgendaDetailAction {
    data object OnCloseDetailIconClick : AgendaDetailAction()
    data object OnEditIconClick : AgendaDetailAction()
    data object OnSaveClick : AgendaDetailAction()
    data object OnDeleteButtonClick : AgendaDetailAction()
    data class OnFromHourMinutesChanged(val millisOfHour: Long, val millisOfMinutes: Long) :
        AgendaDetailAction()
    data class OnToHourMinutesChanged(val millisOfHour: Long, val millisOfMinutes: Long) :
        AgendaDetailAction()
    data class OnFromDateChanged(val millisOfDate: Long) : AgendaDetailAction()
    data class OnToDateChanged(val millisOfDate: Long) : AgendaDetailAction()
    data class OnNotificationOptionSelected(val millisOfNotification: Long) : AgendaDetailAction()
    data object OnNavigateToEditTitleClick : AgendaDetailAction()
    data object OnNavigateToEditDescriptionClick : AgendaDetailAction()
    data class OnTitleChanged(val title: String) : AgendaDetailAction()
    data class OnDescriptionChanged(val description: String) : AgendaDetailAction()
    data class OnAddedPhoto(val imageByteArray: ByteArray) : AgendaDetailAction()
    data class OnPhotoClicked(val photoModel: PhotoModel) : AgendaDetailAction()
    class OnPhotoDeleted(val photoKey: String) : AgendaDetailAction()
    data class OnEmailChanged(val email: String) : AgendaDetailAction()
    data class OnEmailInputFocusChanged(val isFocused: Boolean) : AgendaDetailAction()
    data class OnDeleteAttendee(val userId: String) : AgendaDetailAction()
    data object OnAddVisitorButtonClicked : AgendaDetailAction()
    data object OnCloseAddVisitorDialogClick : AgendaDetailAction()
    data object OnAddVisitorIconClick : AgendaDetailAction()
    data object OnChangeUserAssistanceButtonClick : AgendaDetailAction()
}