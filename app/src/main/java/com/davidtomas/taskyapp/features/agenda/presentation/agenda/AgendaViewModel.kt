package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.core.presentation.util.daysOfWeekIncludingGivenDate
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.domain.useCase.ObserveSelectedDayAgendaUseCase
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class AgendaViewModel(
    private val agendaRepository: AgendaRepository,
    private val observeSelectedDayAgendaUseCase: ObserveSelectedDayAgendaUseCase,
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    private val _selectedDate = MutableStateFlow(ZonedDateTime.now())
    private val selectedDate: StateFlow<ZonedDateTime> = _selectedDate

    init {
        viewModelScope.launch {
            agendaRepository.fetchAgenda().fold(
                onError = {},
                onSuccess = {}
            )
            observeSelectedDayAgendaUseCase(selectedDate).collectLatest {
                state = state.copy(agendaItems = it)
            }
        }
    }

    fun onAction(agendaAction: AgendaAction) {
        when (agendaAction) {
            is AgendaAction.OnDoneBulletClicked -> {
                viewModelScope.launch {
                    taskRepository.saveTask(
                        taskModel = agendaAction.taskModel.copy(
                            isDone = !agendaAction.taskModel.isDone
                        )
                    )
                }
            }

            is AgendaAction.OnAgendaItemOptionsClicked -> {
                state = state.copy(
                    agendaItemWithOpenedOptions = agendaAction.agendaModel
                )
            }

            is AgendaAction.OnDismissedOptionsDropdown -> {
                state = state.copy(
                    agendaItemWithOpenedOptions = null
                )
            }

            is AgendaAction.OnDayClicked -> {
                state = state.copy(
                    dateSelected = agendaAction.dateSelected
                )
                _selectedDate.value = agendaAction.dateSelected
            }

            is AgendaAction.OnDateMonthPickerSelected -> {
                val dateSelected = ZonedDateTime.ofInstant(
                    Instant.ofEpochMilli(agendaAction.millisOfDateSelected),
                    ZoneId.systemDefault()
                )
                state = state.copy(
                    dateSelected = dateSelected,
                    weekShown = dateSelected.daysOfWeekIncludingGivenDate()
                )
                _selectedDate.value = dateSelected
            }
            is AgendaAction.OnDeleteAgendaItemClicked -> {
                viewModelScope.launch {
                    agendaRepository.deleteAgendaItem(agendaAction.agendaModel)
                }
            }

            is AgendaAction.OnInitialsIconClicked -> {
                viewModelScope.launch {
                    authRepository.logout().fold(
                        onError = {},
                        onSuccess = {}
                    )
                }
            }

            else -> Unit
        }
    }
}