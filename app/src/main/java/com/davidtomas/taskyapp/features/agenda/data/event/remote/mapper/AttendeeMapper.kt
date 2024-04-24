package com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.agenda.data.event.remote.response.CheckAttendeeResponse
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel

fun CheckAttendeeResponse.toAttendeeModel(): AttendeeModel? {
    return if (attendee == null) null
    else AttendeeModel(
        email = attendee.email,
        fullName = attendee.fullName,
        userId = attendee.userId,
        eventId = String.EMPTY_STRING,
        isGoing = true,
        remindAt = 0L
    )
}
