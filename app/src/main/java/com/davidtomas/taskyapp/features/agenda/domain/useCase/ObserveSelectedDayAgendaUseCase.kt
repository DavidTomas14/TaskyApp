package com.davidtomas.taskyapp.features.agenda.domain.useCase

import com.davidtomas.taskyapp.core.presentation.util.getStartAndEndOfDayMillis
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import java.time.ZonedDateTime

class ObserveSelectedDayAgendaUseCase(
    private val agendaRepository: AgendaRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(date: Flow<ZonedDateTime>): Flow<List<AgendaModel>> {
        return date.flatMapLatest { date ->
            val (startOfDayMillis, endOfDayMillis) = date.getStartAndEndOfDayMillis()
            agendaRepository.observeAgendaByDate(startOfDayMillis, endOfDayMillis)
        }
    }
}