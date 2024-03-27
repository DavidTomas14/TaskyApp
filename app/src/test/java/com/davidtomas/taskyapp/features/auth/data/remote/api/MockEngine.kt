package com.davidtomas.taskyapp.features.auth.data.remote.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.serialization.json.Json

private val responseHeaders = headersOf(HttpHeaders.ContentType, "application/json")

val ktorSuccessClient = HttpClient(MockEngine) {
    expectSuccess = true
    engine {
        addHandler { request ->
            when (request.url.encodedPath) {
                AuthRoutes.LOGIN_ROUTE -> {
                    respond(
                        LoginSuccessJson,
                        HttpStatusCode.OK,
                        responseHeaders
                    )
                }

                AuthRoutes.REGISTER_ROUTE -> {
                    respond(
                        ByteReadChannel.Empty,
                        HttpStatusCode.OK,
                        responseHeaders
                    )
                }

                AuthRoutes.AUTHENTICATE_ROUTE -> {
                    respond(
                        ByteReadChannel.Empty,
                        HttpStatusCode.OK,
                        responseHeaders
                    )
                }

                else -> error("Unhandled ${request.url.encodedPath}")
            }
        }
    }
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
        )
    }
}

fun ktorErrorClientWithSpecificError(statusCode: HttpStatusCode) =
    HttpClient(MockEngine) {
        expectSuccess = true
        engine {
            addHandler { request ->
                when (request.url.encodedPath) {
                    AuthRoutes.LOGIN_ROUTE -> {
                        respond(
                            CommonErrorJson,
                            statusCode,
                            responseHeaders
                        )
                    }

                    AuthRoutes.REGISTER_ROUTE -> {
                        respond(
                            CommonErrorJson,
                            statusCode,
                            responseHeaders
                        )
                    }

                    else -> error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }

fun ktorExceptionErrorClient(e: Exception) =
    HttpClient(MockEngine) {
        expectSuccess = true
        engine {
            addHandler { request ->
                throw e
            }
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                }
            )
        }
    }

val LoginSuccessJson =
    """
        {
            "token": "token",
            "fullName": "fullName",
            "userId": "userId"
        }
    """.trimIndent()

val CommonErrorJson =
    """
        {
            "message": "Error Message"
        }
    """.trimIndent()

fun createClient(
    json: String,
    statusCode: HttpStatusCode
) = HttpClient(MockEngine) {
    expectSuccess = true
    engine {
        addHandler {
            respond(
                json,
                statusCode,
                responseHeaders
            )
        }
    }
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println(message)
            }
        }
    }

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
        )
    }
}