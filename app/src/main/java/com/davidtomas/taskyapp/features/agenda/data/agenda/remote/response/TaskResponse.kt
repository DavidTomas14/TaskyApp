package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class TaskResponse(
    val description: String,
    val id: String,
    val isDone: Boolean,
    val remindAt: Long,
    val time: Long,
    val title: String
)