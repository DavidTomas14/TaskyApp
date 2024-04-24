package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError

interface LogoutRepository {
    suspend fun logout(): Result<Unit, DataError.Network>
}