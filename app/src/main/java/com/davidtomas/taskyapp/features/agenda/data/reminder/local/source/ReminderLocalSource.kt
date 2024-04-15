package com.davidtomas.taskyapp.features.agenda.data.reminder.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import kotlinx.coroutines.flow.Flow

interface ReminderLocalSource {
    suspend fun saveReminder(reminder: ReminderModel, modificationType: ModificationType? = null)

    suspend fun saveReminders(reminders: List<ReminderModel>)
    suspend fun getReminderByDate(startOfDayMillis: Long, endOfDateMillis: Long): Flow<List<ReminderModel>>

    suspend fun getRemindById(reminderId: String): ReminderModel

    suspend fun deleteReminder(
        reminderId: String,
        modificationType: ModificationType? = null
    )
}