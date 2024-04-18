package com.davidtomas.taskyapp.features.agenda.data.event.remote.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths
import com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper.toEventRequest
import com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper.toUpdateEventRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.path
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventServiceImpl(
    private val client: HttpClient,
) : EventService {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun createEvent(event: EventModel): Result<Unit, DataError.Network> {
        val formData = formData {
            event.photos.filter { it.modificationType == ModificationType.ADD }
                .forEachIndexed { index, photo ->
                    append(
                        key = "photo$index",
                        value = photo.imageData,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${event.photos.first().key}.jpg"
                            )
                        }
                    )
                }
            append(
                key = "create_event_request",
                value = Json.encodeToString(event.toEventRequest()),
                headers = Headers.build {
                    append(HttpHeaders.ContentType, "text/plain")
                    append(
                        HttpHeaders.ContentDisposition,
                        "form-data; name=\"create_event_request\""
                    )
                }
            )
        }
        return client.safeRequest<Unit> {
            url { path(AgendaPaths.EVENT_ROUTE) }
            method = HttpMethod.Post
            setBody(MultiPartFormDataContent(formData))
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override suspend fun updateEvent(event: EventModel): Result<Unit, DataError.Network> {
        val formData = formData {
            event.photos.filter { it.modificationType == ModificationType.ADD }
                .forEachIndexed { index, photo ->
                    append(
                        key = "photo$index",
                        value = photo.imageData,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${event.photos.first().key}.jpg"
                            )
                        }
                    )
                }
            append(
                key = "update_event_request",
                value = Json.encodeToString(event.toUpdateEventRequest()),
                headers = Headers.build {
                    append(HttpHeaders.ContentType, "text/plain")
                    append(
                        HttpHeaders.ContentDisposition,
                        "form-data; name=\"update_event_request\""
                    )
                }
            )
        }
        return client.safeRequest<Unit> {
            url { path(AgendaPaths.EVENT_ROUTE) }
            method = HttpMethod.Put
            setBody(MultiPartFormDataContent(formData))
        }
    }

    override suspend fun deleteEvent(eventId: String): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.EVENT_ROUTE) }
            method = HttpMethod.Delete
            parameter(AgendaPaths.EVENT_ID_PARAM, eventId)
        }
}
