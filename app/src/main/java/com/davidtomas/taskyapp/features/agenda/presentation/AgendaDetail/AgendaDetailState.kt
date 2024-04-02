package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType

data class AgendaDetailState(
    val title: String = String.EMPTY_STRING,
    val description: String = String.EMPTY_STRING,
    val date: Long = 0L,
    val hour: Long = 0L,
    val remindAt: Long = 0L,
    val isEditable: Boolean = false,
    val agendaType: AgendaType = AgendaType.REMINDER
)
