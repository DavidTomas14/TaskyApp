package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.core.presentation.util.extractDayMillis
import com.davidtomas.taskyapp.core.presentation.util.extractFromStartOfTheDayOfDateMillis
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaType
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ScreenMode
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.presentation._common.navigation.AgendaRoutes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID

open class AgendaDetailViewModel(
    private val eventRepository: EventRepository,
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(AgendaDetailState())
        protected set

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
                if (agendaItemId != null) {
                    populateData()
                } else {
                    state = state.copy(
                        title = "New Title",
                        description = "New Description",
                        agendaType = agendaType ?: AgendaType.TASK,
                        screenMode = screenMode
                    )
                }
            }

            else -> Unit
        }
    }

    fun populateData() {
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

                AgendaType.EVENT -> {
                    viewModelScope.launch {
                        val event = eventRepository.getEvent(agendaItemId)
                        state = state.copy(
                            title = event.title,
                            description = event.description,
                            date = ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(event.date),
                                ZoneId.systemDefault()
                            ),
                            remindIn = event.date - event.remindAt,
                            agendaType = AgendaType.EVENT,
                            screenMode = screenMode ?: ScreenMode.REVIEW,
                            photos = event.photos.map { it.uri },
                            attendees = event.attendees
                        )
                    }
                }

                null -> Unit
            }
        }
    }

    open fun onAction(agendaDetailAction: AgendaDetailAction) {
        when (agendaDetailAction) {
            is AgendaDetailAction.OnEditIconClick -> {
                state = state.copy(screenMode = ScreenMode.EDIT_ADD)
            }

            is AgendaDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    val dateMillis = state.date.toInstant().toEpochMilli()
                    val remindAt = dateMillis - state.remindIn
                    when (agendaType) {
                        AgendaType.TASK -> {
                            taskRepository.saveTask(
                                TaskModel(
                                    id = agendaItemId ?: UUID.randomUUID().toString(),
                                    title = state.title,
                                    description = state.description,
                                    date = dateMillis,
                                    remindAt = remindAt,
                                    isDone = false
                                )
                            )
                        }

                        AgendaType.REMINDER -> {
                            reminderRepository.saveReminder(
                                ReminderModel(
                                    id = agendaItemId ?: UUID.randomUUID().toString(),
                                    title = state.title,
                                    description = state.description,
                                    date = dateMillis,
                                    remindAt = remindAt,
                                )
                            )
                        }

                        AgendaType.EVENT -> {
                            eventRepository.saveEvent(
                                EventModel(
                                    id = agendaItemId ?: UUID.randomUUID().toString(),
                                    title = state.title,
                                    description = state.description,
                                    date = dateMillis,
                                    remindAt = remindAt,
                                    isUserEventCreator = true,
                                    attendees = listOf(),
                                    to = 0L,
                                    host = "1234",
                                    photos = state.photos?.let {
                                        it.map { uri ->
                                            PhotoModel(
                                                key = UUID.randomUUID().toString(),
                                                uri = uri
                                            )
                                        }
                                    } ?: listOf()
                                )
                            )
                        }

                        null -> Unit
                    }
                    state = state.copy(
                        screenMode = ScreenMode.REVIEW
                    )
                }
            }

            is AgendaDetailAction.OnDeleteButtonClick -> {
                viewModelScope.launch {
                    when (agendaType) {
                        AgendaType.TASK -> {
                            taskRepository.deleteTask(
                                agendaItemId!!
                            )
                        }

                        AgendaType.REMINDER -> {
                            reminderRepository.deleteReminder(
                                agendaItemId!!
                            )
                        }

                        AgendaType.EVENT -> {
                            eventRepository.deleteEvent(
                                agendaItemId!!
                            )
                        }

                        null -> Unit
                    }
                    _uiEvent.send(AgendaDetailUiEvent.NavigateUp)
                }
            }

            is AgendaDetailAction.OnHourMinutesChanged -> {

                state = state.copy(
                    date = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                            state.date.extractFromStartOfTheDayOfDateMillis() +
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
                            agendaDetailAction.millisOfDate.extractFromStartOfTheDayOfDateMillis() +
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

            is AgendaDetailAction.OnTitleChanged -> {
                state = state.copy(title = agendaDetailAction.title)
            }

            is AgendaDetailAction.OnDescriptionChanged -> {
                state = state.copy(description = agendaDetailAction.description)
            }

            is AgendaDetailAction.OnAddedPhoto -> {
                state = state.copy(
                    photos = state.photos?.plus(agendaDetailAction.photoUri)
                )
            }

            is AgendaDetailAction.OnPhotoDeleted -> {
                state = state.copy(
                    photos = state.photos?.minus(agendaDetailAction.uri)
                )
            }

            else -> Unit
        }
    }
}