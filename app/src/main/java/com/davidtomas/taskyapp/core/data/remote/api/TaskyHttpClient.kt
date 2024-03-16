package com.davidtomas.taskyapp.core.data.remote.api

import android.util.Log
import com.davidtomas.taskyapp.BuildConfig
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.features.auth.data.local.TokenDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

object TaskyHttpClient {

    private const val LOG_TAG = "KtorClient"
    private const val BASE_URL = "https://tasky.pl-coding.com/"

    fun create(tokenDataStore: TokenDataStore) = HttpClient(Android) {
        expectSuccess = true
        defaultRequest {
            url(BASE_URL)
            header("x-api-key", BuildConfig.API_KEY)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i(LOG_TAG, message)
                }
            }
        }
        install(ResponseObserver) {
            onResponse { response ->
                Log.i(LOG_TAG, "${response.status.value}")
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
        install(Auth) {
            bearer {
                loadTokens {
                    tokenDataStore.getToken().first()
                        ?.let { BearerTokens(it, String.EMPTY_STRING) }
                }
            }
        }
    }
}