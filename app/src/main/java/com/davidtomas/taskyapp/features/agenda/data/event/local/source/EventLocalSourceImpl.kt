package com.davidtomas.taskyapp.features.agenda.data.event.local.source

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.EventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.mapper.toEventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.mapper.toEventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.coroutines.cancellation.CancellationException

class EventLocalSourceImpl(
    private val realmDb: Realm
) : EventLocalSource {
    override suspend fun saveEvent(event: EventModel) {
        realmDb.write {
            copyToRealm(event.toEventEntity(), UpdatePolicy.ALL)
        }
    }

    override suspend fun getEvents(): Result<Flow<List<EventModel>>, DataError> {
        return try {
            val events = realmDb
                .query<EventEntity>()
                .asFlow()
                .map { results ->
                    results.list.toList().map { it.toEventModel() }
                }
            Result.Success(events)
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.Error(DataError.Local.OPERATION_FAILED)
        }
    }

    override suspend fun deleteEvent(event: EventModel) {
        realmDb.write {
            val latestEvent = findLatest(event.toEventEntity()) ?: return@write
            delete(latestEvent)
        }
    }
}