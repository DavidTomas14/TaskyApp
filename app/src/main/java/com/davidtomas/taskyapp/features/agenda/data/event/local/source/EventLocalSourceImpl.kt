package com.davidtomas.taskyapp.features.agenda.data.event.local.source

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.EventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.mapper.toEventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.mapper.toEventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventLocalSourceImpl(
    private val realmDb: Realm
) : EventLocalSource {
    override suspend fun saveEvent(event: EventModel) {
        realmDb.write {
            copyToRealm(event.toEventEntity(), UpdatePolicy.ALL)
        }
    }

    override suspend fun saveEvents(events: List<EventModel>) {
        realmDb.write {
            events.forEach { copyToRealm(it.toEventEntity()) }
        }
    }

    override suspend fun getEvents(): Flow<List<EventModel>> = realmDb
        .query<EventEntity>()
        .asFlow()
        .map { results ->
            results.list.toList().map { it.toEventModel() }
        }

    override suspend fun getEventById(eventId: String): EventModel = realmDb
        .query<EventEntity>("id == $0", eventId).find().first()
        .toEventModel()

    override suspend fun deleteEvent(eventId: String) {
        realmDb.write {
            val eventToDelete = query<EventEntity>("id == $0", eventId).find().first()
            delete(eventToDelete)
        }
    }
}