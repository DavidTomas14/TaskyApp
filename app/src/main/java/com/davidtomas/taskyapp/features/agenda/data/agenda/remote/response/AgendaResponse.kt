package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AgendaResponse(
    @SerialName("events") val eventResponses: List<EventResponse>,
    @SerialName("reminders") val reminderResponses: List<ReminderResponse>,
    @SerialName("tasks") val taskResponses: List<TaskResponse>
)