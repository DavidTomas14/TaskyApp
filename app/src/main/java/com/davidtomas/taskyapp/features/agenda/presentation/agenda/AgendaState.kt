package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import com.davidtomas.taskyapp.core.presentation.util.daysOfWeekIncludingGivenDate
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import java.time.DayOfWeek
import java.time.ZonedDateTime

data class AgendaState(
    val agendaItems: List<AgendaModel> = listOf(),
    val agendaItemWithOpenedOptions: AgendaModel? = null,
    val isMonthPickerShown: Boolean = false,
    val isLogoutDropDownShown: Boolean = false,
    val isAddItemDropDownShown: Boolean = false,
    val dateSelected: ZonedDateTime = ZonedDateTime.now(),
    val weekShown: Map<DayOfWeek, ZonedDateTime> = ZonedDateTime.now().daysOfWeekIncludingGivenDate()
)
