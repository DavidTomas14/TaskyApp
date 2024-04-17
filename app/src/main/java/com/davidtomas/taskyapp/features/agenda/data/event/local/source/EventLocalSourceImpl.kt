package com.davidtomas.taskyapp.features.agenda.data.event.local.source

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.EventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.mapper.toEventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.mapper.toEventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventLocalSourceImpl(
    private val realmDb: Realm
) : EventLocalSource {
    override suspend fun saveEvent(event: EventModel, modificationType: ModificationType?) {
        realmDb.write {
            copyToRealm(event.toEventEntity(modificationType), UpdatePolicy.ALL)
        }
    }

    override suspend fun saveEvents(events: List<EventModel>) {
        realmDb.write {
            events.forEach { copyToRealm(it.toEventEntity(), UpdatePolicy.ALL) }
        }
    }

    override suspend fun getEventsByDate(
        startOfDayMillis: Long,
        endOfDateMillis: Long
    ): Flow<List<EventModel>> = realmDb
        .query<EventEntity>("from > $0 && from < $1", startOfDayMillis, endOfDateMillis)
        .asFlow()
        .map { results ->
            results.list.toList().map { it.toEventModel() }
        }

    override suspend fun getEventById(eventId: String): EventModel = realmDb
        .query<EventEntity>("id == $0", eventId).find().first()
        .toEventModel()

    override suspend fun deleteEvent(eventId: String, modificationType: ModificationType?) {
        realmDb.write {
            val eventToDelete = query<EventEntity>("id == $0", eventId).find().first()
            modificationType?.let {
                copyToRealm(
                    eventToDelete.apply {
                        this.modificationType = it.name
                    },
                    UpdatePolicy.ALL
                )
            } ?: run {
                delete(eventToDelete)
            }
        }
    }

    override suspend fun saveAttendee(eventId: String, attendeeModel: AttendeeModel) {
        realmDb.write {
            val eventToUpdate = query<EventEntity>("id == $0", eventId).find().first()
            val actualAttendeeList = eventToUpdate.toEventModel().attendees
            val eventModified =
                eventToUpdate.toEventModel().copy(attendees = actualAttendeeList + attendeeModel)
            copyToRealm(eventModified.toEventEntity(), UpdatePolicy.ALL)
        }
    }
}