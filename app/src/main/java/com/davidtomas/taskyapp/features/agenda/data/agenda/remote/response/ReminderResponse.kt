package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ReminderResponse(
    val description: String,
    val id: String,
    val remindAt: Long,
    val time: Long,
    val title: String
)