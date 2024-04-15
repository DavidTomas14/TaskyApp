package com.davidtomas.taskyapp.features.agenda.data.task.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long,
    val isDone: Boolean
)