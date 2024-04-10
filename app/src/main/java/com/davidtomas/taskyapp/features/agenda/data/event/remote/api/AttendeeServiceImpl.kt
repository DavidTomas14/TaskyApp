package com.davidtomas.taskyapp.features.agenda.data.event.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.map
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths.EMAIL_PARAM
import com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper.toAttendeeModel
import com.davidtomas.taskyapp.features.agenda.data.event.remote.response.CheckAttendeeResponse
import com.davidtomas.taskyapp.features.agenda.domain.model.AttendeeModel
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path

class AttendeeServiceImpl(
    private val client: HttpClient
) : AttendeeService {

    override suspend fun checkAttendee(email: String): Result<AttendeeModel?, DataError.Network> =
        client.safeRequest<CheckAttendeeResponse> {
            url { path(AgendaPaths.CHECK_ATTENDEE_ROUTE) }
            method = HttpMethod.Get
            parameter(EMAIL_PARAM, email)
            contentType(ContentType.Application.Json)
        }.map {
            it.toAttendeeModel()
        }

}