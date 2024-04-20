package com.davidtomas.taskyapp.features.agenda.domain.model

sealed interface AgendaModel {
    val id: String
    val title: String
    val description: String
    val date: Long
    val remindAt: Long
    val agendaType: AgendaType
}