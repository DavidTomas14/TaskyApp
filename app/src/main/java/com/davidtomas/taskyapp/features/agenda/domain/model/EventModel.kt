package com.davidtomas.taskyapp.features.agenda.domain.model

data class EventModel(
    val id: String,
    val title: String,
    val description: String,
    override val date: Long,
    val to: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val attendees: List<AttendeeModel>,
    val photos: List<PhotoModel>
) : AgendaItem
