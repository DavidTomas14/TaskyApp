package com.davidtomas.taskyapp.features.agenda.data._common.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    val attendees: List<AttendeeResponse>,
    val description: String,
    val from: Long,
    val host: String,
    val id: String,
    val isUserEventCreator: Boolean,
    @SerialName("photos") val photoResponses: List<PhotoResponse>,
    val remindAt: Long,
    val title: String,
    val to: Long
)