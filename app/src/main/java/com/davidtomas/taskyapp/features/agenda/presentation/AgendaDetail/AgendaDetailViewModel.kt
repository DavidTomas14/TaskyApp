package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.core.presentation.util.extractDateMillis
import com.davidtomas.taskyapp.core.presentation.util.extractDayMillis
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class AgendaDetailViewModel(
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AgendaDetailState())
        private set

    private val _uiEvent = Channel<AgendaDetailUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val agendaType =
        savedStateHandle.get<String>(AgendaRoutes.AGENDA_TYPE_PARAM)?.let { AgendaType.valueOf(it) }
    private val screenMode =
        savedStateHandle.get<String>(AgendaRoutes.SCREEN_MODE_PARAM)?.let { ScreenMode.valueOf(it) }
    private val agendaItemId = savedStateHandle.get<String>(AgendaRoutes.AGENDA_ITEM_ID_PARAM)

    init {
        when (screenMode) {
            ScreenMode.REVIEW -> {
                populateData()
            }

            ScreenMode.EDIT_ADD -> {
                if (agendaType != null) {
                    populateData()
                } else {
                    state = state.copy(
                        title = "New Title",
                        description = "New Description"
                    )
                }
            }

            else -> Unit
        }
    }

    private fun populateData() {
        agendaItemId?.let {
            when (agendaType) {
                AgendaType.REMINDER -> {
                    viewModelScope.launch {
                        val reminder = reminderRepository.getReminder(agendaItemId)
                        state = state.copy(
                            title = reminder.title,
                            description = reminder.description,
                            date = ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(reminder.date),
                                ZoneId.systemDefault()
                            ),
                            remindIn = reminder.date - reminder.remindAt,
                            agendaType = AgendaType.REMINDER,
                            screenMode = screenMode ?: ScreenMode.REVIEW
                        )
                    }
                }

                AgendaType.TASK -> {
                    viewModelScope.launch {
                        val task = taskRepository.getTask(agendaItemId)
                        state = state.copy(
                            title = task.title,
                            description = task.description,
                            date = ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(task.date),
                                ZoneId.systemDefault()
                            ),
                            remindIn = task.date - task.remindAt,
                            agendaType = AgendaType.TASK,
                            screenMode = screenMode ?: ScreenMode.REVIEW
                        )
                    }
                }

                else -> Unit
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    fun onAction(agendaDetailAction: AgendaDetailAction) {
        when (agendaDetailAction) {
            is AgendaDetailAction.OnEditIconClick -> {
                state = state.copy(screenMode = ScreenMode.EDIT_ADD)
            }

            is AgendaDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    val dateMillis = state.date.toInstant().toEpochMilli()
                    val remindAt = dateMillis - state.remindIn
                    if (agendaType == AgendaType.TASK) {
                        taskRepository.saveTask(
                            TaskModel(
                                id = agendaItemId!!,
                                title = "Task Editado",
                                description = state.description,
                                date = dateMillis,
                                remindAt = remindAt,
                                isDone = false
                            )
                        )
                    } else {
                        reminderRepository.saveReminder(
                            ReminderModel(
                                id = agendaItemId!!,
                                title = "Reminder Editado",
                                description = state.description,
                                date = dateMillis,
                                remindAt = remindAt,
                            )
                        )
                    }
                    state = state.copy(
                        screenMode = ScreenMode.REVIEW
                    )
                }
            }

            is AgendaDetailAction.OnDeleteButtonClick -> {
                viewModelScope.launch {
                    if (agendaType == AgendaType.TASK) {
                        taskRepository.deleteTask(
                            agendaItemId!!
                        )
                    } else {
                        reminderRepository.deleteReminder(
                            agendaItemId!!
                        )
                    }
                    _uiEvent.send(AgendaDetailUiEvent.NavigateUp)
                }
            }

            is AgendaDetailAction.OnHourMinutesChanged -> {

                state = state.copy(
                    date = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                            state.date.extractDateMillis() +
                                agendaDetailAction.millisOfHour +
                                agendaDetailAction.millisOfMinutes
                        ),
                        ZoneId.systemDefault()
                    ),
                )
            }

            is AgendaDetailAction.OnDateChanged -> {
                state = state.copy(
                    date = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                            agendaDetailAction.millisOfDate.extractDateMillis() +
                                state.date.extractDayMillis()
                        ),
                        ZoneId.systemDefault()
                    ),
                )
            }

            is AgendaDetailAction.OnNotificationOptionSelected -> {
                state = state.copy(
                    remindIn = agendaDetailAction.millisOfNotification
                )
            }

            else -> Unit
        }
    }
}