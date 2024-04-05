package com.davidtomas.taskyapp.features.agenda.data.reminder.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import kotlinx.coroutines.flow.Flow

interface ReminderLocalSource {
    suspend fun saveReminder(reminder: ReminderModel)

    suspend fun getReminder(): Flow<List<ReminderModel>>

    suspend fun getRemindById(reminderId: String): ReminderModel

    suspend fun deleteReminder(reminderId: String)
}