package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper

import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.AttendeeResponse
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.EventResponse
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.PhotoResponse
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.ReminderResponse
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.TaskResponse
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel

fun EventResponse.toEventModel() = EventModel(
    id = id,
    title = title,
    description = description,
    date = from,
    to = to,
    remindAt = remindAt,
    host = host,
    isUserEventCreator = isUserEventCreator,
    attendees = attendees.map {
        it.toAttendeeModel()
    },
    photos = photoResponses.map {
        it.toPhotoModel()
    }
)

fun AttendeeResponse.toAttendeeModel() = AttendeeModel(
    email = email,
    fullName = fullName,
    eventId = eventId,
    isGoing = isGoing,
    remindAt = remindAt,
)

fun PhotoResponse.toPhotoModel() = PhotoModel(
    key = key,
    url = url,
)

fun TaskResponse.toTaskModel() =
    TaskModel(
        id = id,
        title = this.title,
        description = this.description,
        date = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone
    )

fun ReminderResponse.toReminderModel() =
    ReminderModel(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.time,
        remindAt = this.remindAt,
    )