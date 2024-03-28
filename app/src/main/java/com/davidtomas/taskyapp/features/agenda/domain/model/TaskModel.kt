package com.davidtomas.taskyapp.features.agenda.domain.model

class TaskModel(
    val id: String,
    val title: String,
    val description: String,
    override val date: Long,
    val remindAt: Long,
    val isDone: Boolean,
) : AgendaItem