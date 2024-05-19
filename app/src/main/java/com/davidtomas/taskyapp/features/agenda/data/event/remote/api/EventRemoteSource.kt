package com.davidtomas.taskyapp.features.agenda.data.event.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel

interface EventRemoteSource {
    suspend fun createEvent(event: EventModel): Result<Unit, DataError.Network>
    suspend fun updateEvent(event: EventModel): Result<Unit, DataError.Network>
    suspend fun deleteEvent(eventId: String): Result<Unit, DataError.Network>
}
