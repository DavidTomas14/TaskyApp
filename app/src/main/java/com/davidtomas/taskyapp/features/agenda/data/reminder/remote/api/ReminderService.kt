package com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel

interface ReminderService {

    suspend fun createReminder(reminder: ReminderModel): Result<Unit, DataError.Network>
    suspend fun updateReminder(reminder: ReminderModel): Result<Unit, DataError.Network>

    suspend fun deleteReminder(reminderId: String): Result<Unit, DataError.Network>
}