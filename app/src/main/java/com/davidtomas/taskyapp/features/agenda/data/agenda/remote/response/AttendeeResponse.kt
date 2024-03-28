package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response

import kotlinx.serialization.Serializable

@Serializable

data class AttendeeResponse(
    val email: String,
    val fullName: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)