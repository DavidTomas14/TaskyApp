package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {
    suspend fun observeAgendaByDate(startOfDayMillis: Long, endOfDateMillis: Long): Flow<List<AgendaModel>>

    suspend fun fetchAgenda(): Result<Unit, DataError.Network>
    suspend fun getAgenda(): List<AgendaModel>

    suspend fun clearTables()
    suspend fun getFutureAgendaItems(): List<AgendaModel>
}