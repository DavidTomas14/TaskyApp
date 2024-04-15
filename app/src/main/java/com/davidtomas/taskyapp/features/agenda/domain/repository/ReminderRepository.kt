package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel

interface ReminderRepository {
    suspend fun saveReminder(reminderModel: ReminderModel, modificationType: ModificationType)

    suspend fun getReminder(reminderId: String): ReminderModel

    suspend fun deleteReminder(reminderId: String)
}