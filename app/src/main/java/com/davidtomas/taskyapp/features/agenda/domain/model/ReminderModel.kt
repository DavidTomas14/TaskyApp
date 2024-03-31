package com.davidtomas.taskyapp.features.agenda.domain.model

data class ReminderModel(
    override val id: String,
    override val title: String,
    override val description: String,
    override val date: Long,
    val remindAt: Long
) : AgendaModel