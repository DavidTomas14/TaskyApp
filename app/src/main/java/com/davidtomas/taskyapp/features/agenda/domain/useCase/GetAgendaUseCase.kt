package com.davidtomas.taskyapp.features.agenda.domain.useCase

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.agenda.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaItem
import kotlinx.coroutines.flow.Flow

class GetAgendaUseCase(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(): Result<Flow<List<AgendaItem>>, DataError.Network> =
        agendaRepository.getAgenda()
}