package com.davidtomas.taskyapp.features.agenda.data.event.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths
import com.davidtomas.taskyapp.features.agenda.data.event.remote.mapper.toEventRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class EventServiceImpl(
    private val client: HttpClient,
) : EventService {

    override suspend fun createEvent(event: EventModel): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.EVENT_ROUTE) }
            method = HttpMethod.Post
            val formData = MultiPartFormDataContent(
                formData {
                    // Add JSON part
                    append(
                        "create_event_request",
                        Json.encodeToString(event.toEventRequest()),
                        Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        }
                    )

                    // TODO
                    /*photos.forEachIndexed { index, photo ->
                        append(
                            key = "photo$index",
                            value = photo.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                                append(HttpHeaders.ContentDisposition, "filename=${photo.name}")
                            }
                        )
                    }*/
                }
            )
            setBody(formData)
        }

    override suspend fun updateEvent(event: EventModel): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.EVENT_ROUTE) }
            method = HttpMethod.Put
            contentType(ContentType.Application.Json)
            val formData = MultiPartFormDataContent(
                formData {
                    // Add JSON part
                    append(
                        "update_event_request",
                        Json.encodeToString(event.toEventRequest()),
                        Headers.build {
                            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                        }
                    )

                    /*photos.forEachIndexed { index, photo ->
                        append(
                            key = "photo$index",
                            value = photo.readBytes(),
                            headers = Headers.build {
                                append(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                                append(HttpHeaders.ContentDisposition, "filename=${photo.name}")
                            }
                        )
                    }*/
                }
            )
            setBody(formData)
        }
}
