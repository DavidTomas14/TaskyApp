package com.davidtomas.taskyapp.features.agenda.data.event.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeService
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.EventService
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository

class EventRepositoryImpl(
    private val eventLocalSource: EventLocalSource,
    private val attendeeService: AttendeeService,
    private val eventService: EventService
) : EventRepository {

    override suspend fun saveEvent(eventModel: EventModel, modificationType: ModificationType) {
        when (modificationType) {
            ModificationType.EDIT -> eventService.updateEvent(eventModel)
            else -> eventService.createEvent(eventModel)
        }.fold(onSuccess = {
            eventLocalSource.saveEvent(eventModel)
        }, onError = {
            eventLocalSource.saveEvent(eventModel, modificationType)
        })
    }

    override suspend fun getEvent(eventId: String): EventModel =
        eventLocalSource.getEventById(eventId = eventId)

    override suspend fun saveAttendee(eventId: String, attendeeModel: AttendeeModel) =
        eventLocalSource.saveAttendee(eventId, attendeeModel)

    override suspend fun checkAttendee(email: String): Result<AttendeeModel?, DataError.Network> =
        attendeeService.checkAttendee(email)

    override suspend fun deleteEvent(eventId: String) {
        eventService.deleteEvent(eventId = eventId)
            .fold(
                onSuccess = {
                    eventLocalSource.deleteEvent(eventId = eventId)
                },
                onError = {
                    eventLocalSource.deleteEvent(eventId = eventId, ModificationType.DELETE)
                }
            )
    }
}