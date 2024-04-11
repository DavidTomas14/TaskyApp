package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import java.time.ZonedDateTime

sealed class AgendaAction {
    data class OnAgendaItemOptionsClicked(val agendaModel: AgendaModel) : AgendaAction()
    data object OnDismissedOptionsDropdown : AgendaAction()
    data class OnAgendaItemClicked(val agendaModel: AgendaModel) : AgendaAction()
    data class OnDoneBulletClicked(val taskModel: TaskModel) : AgendaAction()
    data class OnAddAgendaItemOptionClicked(val agendaType: AgendaType) : AgendaAction()
    data class OnOpenAgendaItemClicked(val agendaModel: AgendaModel) : AgendaAction()
    data class OnEditAgendaItemClicked(val agendaModel: AgendaModel) : AgendaAction()
    data class OnDeleteAgendaItemClicked(val agendaModel: AgendaModel) : AgendaAction()
    class OnDayClicked(val dateSelected: ZonedDateTime) : AgendaAction()
    class OnDateMonthPickerSelected(val millisOfDateSelected: Long) : AgendaAction()
    data object OnInitialsIconClicked : AgendaAction()
}