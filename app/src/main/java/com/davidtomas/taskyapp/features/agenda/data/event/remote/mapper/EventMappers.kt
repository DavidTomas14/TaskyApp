package com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.remote.request.EventRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel

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