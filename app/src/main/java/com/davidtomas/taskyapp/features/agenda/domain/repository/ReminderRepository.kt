package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel

interface ReminderRepository {
    suspend fun saveReminder(reminderModel: ReminderModel)

    suspend fun getReminder(reminderId: String): ReminderModel
}