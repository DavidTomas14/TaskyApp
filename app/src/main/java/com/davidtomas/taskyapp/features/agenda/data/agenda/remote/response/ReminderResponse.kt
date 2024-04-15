package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ReminderResponse(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)