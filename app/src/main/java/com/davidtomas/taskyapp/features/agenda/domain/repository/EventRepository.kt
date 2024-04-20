package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType

interface EventRepository {
    suspend fun saveEvent(eventModel: EventModel, modificationType: ModificationType)

    suspend fun getEvent(eventId: String): EventModel

    suspend fun saveAttendee(eventId: String, attendeeModel: AttendeeModel)

    suspend fun checkAttendee(email: String): Result<AttendeeModel?, DataError.Network>

    suspend fun deleteEvent(eventModel: EventModel)
}