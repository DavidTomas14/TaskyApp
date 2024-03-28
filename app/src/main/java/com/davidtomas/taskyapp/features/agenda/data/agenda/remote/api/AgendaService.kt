package com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.response.AgendaResponse

interface AgendaService {

    suspend fun getAgenda(): Result<AgendaResponse, DataError.Network>
}