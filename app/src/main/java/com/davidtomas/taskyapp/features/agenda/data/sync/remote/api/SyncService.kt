package com.davidtomas.taskyapp.features.agenda.data.sync.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths
import com.davidtomas.taskyapp.features.agenda.data.sync.remote.request.SyncAgendaRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class SyncService(
    private val client: HttpClient,
) {
    suspend fun syncAgendaItems(
        deletedEventsIds: List<String>,
        deletedTasksIds: List<String>,
        deletedRemindersIds: List<String>
    ): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.SYNC_AGENDA_ROUTE) }
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(
                SyncAgendaRequest(
                    deletedEventIds = deletedEventsIds,
                    deletedTaskIds = deletedTasksIds,
                    deletedReminderIds = deletedRemindersIds
                )
            )
        }
}