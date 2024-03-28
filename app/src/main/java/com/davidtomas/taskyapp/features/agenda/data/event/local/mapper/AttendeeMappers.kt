package com.davidtomas.taskyapp.features.agenda.data.event.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.AttendeeEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel

fun AttendeeEntity.toAttendeeModel() = AttendeeModel(
    email = email,
    fullName = fullName,
    eventId = eventId,
    isGoing = isGoing,
    remindAt = remindAt
)

fun AttendeeModel.toAttendeeEntity() = AttendeeEntity().apply {
    email = this@toAttendeeEntity.email
    fullName = this@toAttendeeEntity.fullName
    eventId = this@toAttendeeEntity.eventId
    isGoing = this@toAttendeeEntity.isGoing
    remindAt = this@toAttendeeEntity.remindAt
}