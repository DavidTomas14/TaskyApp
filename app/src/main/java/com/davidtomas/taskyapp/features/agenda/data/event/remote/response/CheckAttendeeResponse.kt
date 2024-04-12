package com.davidtomas.taskyapp.features.agenda.data.event.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CheckAttendeeResponse(
    val doesUserExist: Boolean,
    val attendee: AttendeeDetailsResponse?
)

@Serializable
data class AttendeeDetailsResponse(
    val email: String,
    val fullName: String,
    val userId: String,
)
