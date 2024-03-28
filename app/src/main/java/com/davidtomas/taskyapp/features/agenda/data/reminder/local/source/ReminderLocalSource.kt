package com.davidtomas.taskyapp.features.agenda.data.reminder.local.source

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import kotlinx.coroutines.flow.Flow

interface ReminderLocalSource {
    suspend fun saveReminder(event: ReminderModel)

    suspend fun getReminder(): Result<Flow<List<ReminderModel>>, DataError>

    suspend fun deleteReminder(event: ReminderModel)
}