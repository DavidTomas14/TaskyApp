package com.davidtomas.taskyapp.features.agenda.data.event.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.EventRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationScheduler
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository

class EventRepositoryImpl(
    private val eventLocalSource: EventLocalSource,
    private val attendeeRemoteSource: AttendeeRemoteSource,
    private val eventRemoteSource: EventRemoteSource,
    private val notificationScheduler: NotificationScheduler,
) : EventRepository {

    override suspend fun saveEvent(
        eventModel: EventModel,
        modificationType: ModificationType
    ) {
        when (modificationType) {
            ModificationType.EDIT -> eventRemoteSource.updateEvent(eventModel)
            else -> eventRemoteSource.createEvent(eventModel)
        }.fold(onSuccess = {
            eventLocalSource.saveEvent(eventModel)
            notificationScheduler.cancelScheduledNotificationAndPendingIntent(eventModel)
            notificationScheduler.scheduleNotification(eventModel)
        }, onError = {
            eventLocalSource.saveEvent(eventModel, modificationType)
        })
    }

    override suspend fun getEvent(eventId: String): EventModel =
        eventLocalSource.getEventById(eventId = eventId)

    override suspend fun saveAttendee(eventId: String, attendeeModel: AttendeeModel) =
        eventLocalSource.saveAttendee(eventId, attendeeModel)

    override suspend fun checkAttendee(email: String): Result<AttendeeModel?, DataError.Network> =
        attendeeRemoteSource.checkAttendee(email)

    override suspend fun deleteEvent(eventModel: EventModel) {
        eventRemoteSource.deleteEvent(eventId = eventModel.id)
            .fold(
                onSuccess = {
                    eventLocalSource.deleteEvent(eventId = eventModel.id)
                    notificationScheduler.cancelScheduledNotificationAndPendingIntent(eventModel)
                },
                onError = {
                    eventLocalSource.deleteEvent(eventId = eventModel.id, ModificationType.DELETE)
                }
            )
    }
}