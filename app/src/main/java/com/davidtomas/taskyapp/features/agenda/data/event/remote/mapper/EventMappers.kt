package com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.remote.request.EventRequest
import com.davidtomas.taskyapp.features.agenda.data.event.remote.request.UpdateEventRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType

fun EventModel.toEventRequest() = EventRequest(
    id = id,
    title = title,
    description = description,
    from = date,
    to = toDate,
    remindAt = remindAt,
    attendeeIds = attendees.map {
        it.userId
    }
)

fun EventModel.toUpdateEventRequest() = UpdateEventRequest(
    id = id,
    title = title,
    description = description,
    from = date,
    to = toDate,
    remindAt = remindAt,
    attendeeIds = attendees.map {
        it.userId
    },
    deletedPhotoKeys = photos.filter { it.modificationType == ModificationType.DELETE }
        .map { it.key },
    isGoing = false
)