package com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.mapper.toReminderRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class ReminderRemoteSourceImpl(
    private val client: HttpClient,
) : ReminderRemoteSource {

    override suspend fun createReminder(reminder: ReminderModel): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.REMINDER_ROUTE) }
            method = HttpMethod.Post
            setBody(reminder.toReminderRequest())
        }

    override suspend fun updateReminder(reminder: ReminderModel): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.REMINDER_ROUTE) }
            method = HttpMethod.Put
            setBody(reminder.toReminderRequest())
        }

    override suspend fun deleteReminder(reminderId: String): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.REMINDER_ROUTE) }
            method = HttpMethod.Delete
            parameter(AgendaPaths.REMINDER_ID_PARAM, reminderId)
        }
}
