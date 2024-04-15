package com.davidtomas.taskyapp.features.agenda.data.reminder.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ReminderRequest(
    val id: String,
    val title: String,
    val description: String,
    val time: Long,
    val remindAt: Long
)