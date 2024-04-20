package com.davidtomas.taskyapp.features.agenda.domain.model

data class TaskModel(
    override val id: String,
    override val title: String,
    override val description: String,
    override val date: Long,
    override val remindAt: Long,
    val isDone: Boolean,
    override val agendaType: AgendaType = AgendaType.TASK
) : AgendaModel