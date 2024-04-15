package com.davidtomas.taskyapp.features.agenda.data.event.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class EventRequest(
    val id: String,
    val title: String,
    val description: String,
    val to: Long,
    val from: Long,
    val remindAt: Long,
    val attendeeIds: List<String>,
)