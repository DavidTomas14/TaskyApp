package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel

data class AgendaState(
    val agendaItems: List<AgendaModel> = listOf(),
    val agendaItemWithOpenedOptions: AgendaModel? = null,
    val isMonthPickerShown: Boolean = false,
    val isLogoutDropDownShown: Boolean = false,
    val isAddItemDropDownShown: Boolean = false,
)
