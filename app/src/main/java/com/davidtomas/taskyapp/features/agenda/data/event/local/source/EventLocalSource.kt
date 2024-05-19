package com.davidtomas.taskyapp.features.agenda.data.event.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import kotlinx.coroutines.flow.Flow

interface EventLocalSource {
    suspend fun saveEvent(event: EventModel, modificationType: ModificationType? = null)
    suspend fun saveEvents(events: List<EventModel>)

    suspend fun getEventsByDate(startOfDayMillis: Long, endOfDateMillis: Long): Flow<List<EventModel>>
    suspend fun getEvents(): List<EventModel>
    suspend fun getFutureEvents(): List<EventModel>

    suspend fun getEventById(eventId: String): EventModel

    suspend fun deleteEvent(eventId: String, modificationType: ModificationType? = null)
    suspend fun saveAttendee(eventId: String, attendeeModel: AttendeeModel)
    suspend fun getUnsyncedDeletedEvents(): List<String>
    suspend fun getUnsyncedCreatedEvents(): List<EventModel>
    suspend fun getUnsyncedUpdatedEvents(): List<EventModel>
    suspend fun clearRealmTables()
}