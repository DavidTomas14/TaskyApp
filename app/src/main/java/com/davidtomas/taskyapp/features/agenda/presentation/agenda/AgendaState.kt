package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaItem

data class AgendaState(
    val agendaItems: List<AgendaItem> = listOf()
)
