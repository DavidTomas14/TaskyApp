package com.davidtomas.taskyapp.features.agenda.data.event.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventLocalSource {
    suspend fun saveEvent(event: EventModel)
    suspend fun saveEvents(events: List<EventModel>)

    suspend fun getEvents(): Flow<List<EventModel>>

    suspend fun getEventById(eventId: String): EventModel

    suspend fun deleteEvent(eventId: String)
}