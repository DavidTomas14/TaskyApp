package com.davidtomas.taskyapp.features.agenda.data.logout.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import io.ktor.http.HttpMethod
import io.ktor.http.path

class LogoutServiceImpl(
    val client: HttpClient,
) : LogoutService {

    override suspend fun logout(): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(LOGOUT_ROUTE) }
            method = HttpMethod.Get
        }.also {
            client.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
                .firstOrNull()
                ?.clearToken()
        }

    companion object {
        const val LOGOUT_ROUTE = "/logout"
    }
}
