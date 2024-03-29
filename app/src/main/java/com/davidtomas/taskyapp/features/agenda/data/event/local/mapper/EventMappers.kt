package com.davidtomas.taskyapp.features.agenda.data.event.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.EventEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import io.realm.kotlin.ext.toRealmList

fun EventEntity.toEventModel() = EventModel(
    id = id,
    title = title,
    description = description,
    date = from,
    to = to,
    remindAt = remindAt,
    host = host,
    isUserEventCreator = isUserEventCreator,
    attendees = attendees.map { it.toAttendeeModel() },
    photos = photos.map { it.toPhotoModel() }
)

fun EventModel.toEventEntity() = EventEntity().apply {
    id = this@toEventEntity.id
    title = this@toEventEntity.title
    description = this@toEventEntity.description
    from = this@toEventEntity.date
    to = this@toEventEntity.to
    remindAt = this@toEventEntity.remindAt
    host = this@toEventEntity.host
    isUserEventCreator = this@toEventEntity.isUserEventCreator
    attendees = this@toEventEntity.attendees.map { it.toAttendeeEntity() }.toRealmList()
    photos = this@toEventEntity.photos.map { it.toPhotoEntity() }.toRealmList()
}