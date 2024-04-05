package com.davidtomas.taskyapp.features.agenda.data.reminder.repository

import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository

class ReminderRepositoryImpl(
    private val reminderLocalSource: ReminderLocalSource
) : ReminderRepository {
    override suspend fun saveReminder(reminderModel: ReminderModel) =
        reminderLocalSource.saveReminder(reminderModel)

    override suspend fun getReminder(reminderId: String): ReminderModel =
        reminderLocalSource.getRemindById(reminderId = reminderId)

    override suspend fun deleteReminder(reminderId: String) {
        reminderLocalSource.deleteReminder(reminderId = reminderId)
    }
}