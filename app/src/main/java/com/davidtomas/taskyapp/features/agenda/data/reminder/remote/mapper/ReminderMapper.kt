package com.davidtomas.taskyapp.features.agenda.data.reminder.remote.mapper

import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.request.ReminderRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel

fun ReminderModel.toReminderRequest() =
    ReminderRequest(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.date,
        remindAt = this.remindAt,
    )