package com.davidtomas.taskyapp.features.agenda.domain.model

class ReminderModel(
    val id: String,
    val title: String,
    val description: String,
    override val date: Long,
    val remindAt: Long
) : AgendaItem