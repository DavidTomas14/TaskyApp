package com.davidtomas.taskyapp.features.agenda.data.event.local.source

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventLocalSource {
    suspend fun saveEvent(event: EventModel)

    suspend fun getEvents(): Result<Flow<List<EventModel>>, DataError>

    suspend fun deleteEvent(event: EventModel)
}