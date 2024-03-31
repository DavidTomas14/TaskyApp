package com.davidtomas.taskyapp.features.agenda.domain.model

data class TaskModel(
    override val id: String,
    override val title: String,
    override val description: String,
    override val date: Long,
    val remindAt: Long,
    val isDone: Boolean,
) : AgendaModel