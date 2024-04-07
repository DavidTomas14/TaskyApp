package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AgendaViewModel(
    private val agendaRepository: AgendaRepository,
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    init {
        viewModelScope.launch {
            agendaRepository.fetchAgenda().fold(
                onError = {},
                onSuccess = {}
            )
            agendaRepository.observeAgenda().collectLatest {
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

            is AgendaAction.OnDaySelected -> TODO()

            AgendaAction.OnMonthPickerClicked -> TODO()
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