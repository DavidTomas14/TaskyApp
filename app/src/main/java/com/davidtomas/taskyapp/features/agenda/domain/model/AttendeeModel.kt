package com.davidtomas.taskyapp.features.agenda.domain.model

data class AttendeeModel(
    val userId: String,
    val email: String,
    val fullName: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long
)