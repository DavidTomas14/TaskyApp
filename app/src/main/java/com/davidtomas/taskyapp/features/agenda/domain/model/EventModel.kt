package com.davidtomas.taskyapp.features.agenda.domain.model

data class EventModel(
    override val id: String,
    override val title: String,
    override val description: String,
    override val date: Long,
    val toDate: Long,
    val remindAt: Long,
    val host: String,
    val isUserEventCreator: Boolean,
    val attendees: List<AttendeeModel>,
    val photos: List<PhotoModel>
) : AgendaModel
