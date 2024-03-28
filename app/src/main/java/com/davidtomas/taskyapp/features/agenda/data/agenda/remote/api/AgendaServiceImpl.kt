package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaRoutes
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.AgendaResponse
import io.ktor.client.HttpClient
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class AgendaServiceImpl(
    val client: HttpClient,
) : AgendaService {
    override suspend fun getAgenda(): Result<AgendaResponse, DataError.Network> =
        client.safeRequest<AgendaResponse> {
            url { path(AgendaRoutes.GET_FULL_AGENDA_ROUTE) }
            method = HttpMethod.Get
            contentType(ContentType.Application.Json)
        }
}
