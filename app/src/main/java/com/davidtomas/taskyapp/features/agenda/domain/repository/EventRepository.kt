package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel

interface EventRepository {
    suspend fun saveEvent(eventModel: EventModel)

    suspend fun getEvent(eventId: String): EventModel

    suspend fun deleteEvent(eventId: String)
}