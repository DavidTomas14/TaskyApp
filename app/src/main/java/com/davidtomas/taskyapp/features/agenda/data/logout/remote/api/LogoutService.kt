package com.davidtomas.taskyapp.features.agenda.data.logout.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError

interface LogoutService {
    suspend fun logout(): Result<Unit, DataError.Network>
}