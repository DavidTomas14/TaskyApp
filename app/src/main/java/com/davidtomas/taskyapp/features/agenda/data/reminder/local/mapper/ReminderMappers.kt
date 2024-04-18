package com.davidtomas.taskyapp.features.agenda.data.reminder.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.reminder.local.entity.ReminderEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel

fun ReminderEntity.toReminderModel() =
    ReminderModel(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.time,
        remindAt = this.remindAt,
    )

fun ReminderModel.toReminderEntity(modType: ModificationType? = null) =
    ReminderEntity().apply {
        id = this@toReminderEntity.id
        title = this@toReminderEntity.title
        description = this@toReminderEntity.description
        time = this@toReminderEntity.date
        remindAt = this@toReminderEntity.remindAt
        syncType = modType?.name
    }