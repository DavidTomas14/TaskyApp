package com.davidtomas.taskyapp.features.agenda.data.event.repository

import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository

class EventRepositoryImpl(
    private val eventLocalSource: EventLocalSource
) : EventRepository {

    override suspend fun saveEvent(eventModel: EventModel) =
        eventLocalSource.saveEvent(eventModel)

    override suspend fun getEvent(eventId: String): EventModel =
        eventLocalSource.getEventById(eventId = eventId)

    override suspend fun deleteEvent(eventId: String) =
        eventLocalSource.deleteEvent(eventId = eventId)
}