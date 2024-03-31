package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import kotlinx.coroutines.flow.Flow

interface AgendaRepository {
    suspend fun observeAgenda(): Flow<List<AgendaModel>>

    suspend fun deleteAgendaItem(agendaModel: AgendaModel)
}