package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import kotlinx.coroutines.launch

class AgendaDetailViewModel(
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AgendaDetailState())
        private set

    private val agendaType =
        savedStateHandle.get<String>(AgendaRoutes.AGENDA_TYPE_PARAM)?.let { AgendaType.valueOf(it) }
    private val agendaItemId = savedStateHandle.get<String>(AgendaRoutes.AGENDA_ITEM_ID_PARAM)

    init {
        if (agendaItemId != null) {
            when (agendaType) {
                AgendaType.REMINDER -> {
                    viewModelScope.launch {
                        val reminder = reminderRepository.getReminder(agendaItemId)
                        state = state.copy(
                            title = reminder.title,
                            description = reminder.description,
                            date = reminder.date,
                            remindAt = reminder.remindAt,
                            agendaType = AgendaType.REMINDER,
                        )
                    }
                }

                AgendaType.TASK -> {
                    viewModelScope.launch {
                        val task = taskRepository.getTask(agendaItemId)
                        state = state.copy(
                            title = task.title,
                            description = task.description,
                            date = task.date,
                            remindAt = task.remindAt,
                            agendaType = AgendaType.TASK,
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    fun onAction(agendaAction: AgendaDetailAction) {
        when (agendaAction) {
            is AgendaDetailAction.OnEditIconClick -> {
                state = state.copy(isEditable = true)
            }

            is AgendaDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    if (agendaType == AgendaType.TASK) {
                        taskRepository.saveTask(
                            TaskModel(
                                id = agendaItemId!!,
                                title = "Task Editado",
                                description = state.description,
                                date = state.date,
                                remindAt = state.remindAt,
                                isDone = false
                            )
                        )
                    } else {
                        reminderRepository.saveReminder(
                            ReminderModel(
                                id = agendaItemId!!,
                                title = "Reminder Editado",
                                description = state.description,
                                date = state.date,
                                remindAt = state.remindAt,
                            )
                        )
                    }
                }
            }

            else -> Unit
        }
    }
}