package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AgendaViewModel(
    private val agendaRepository: AgendaRepository,
    private val taskRepository: TaskRepository,
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    init {
        viewModelScope.launch {
            state = state.copy(
                agendaItems = agendaRepository.observeAgenda().first()
            )
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

            AgendaAction.OnAddAddAgendaItemFabClicked -> TODO()
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

            else -> Unit
        }
    }
}