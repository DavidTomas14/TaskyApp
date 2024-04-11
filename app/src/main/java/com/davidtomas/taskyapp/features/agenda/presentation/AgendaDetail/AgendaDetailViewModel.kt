package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.core.presentation.util.UiText
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
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateEmailUseCase
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
    private val savedStateHandle: SavedStateHandle,
    private val validateEmailUseCase: ValidateEmailUseCase,
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
                            toDate = ZonedDateTime.ofInstant(
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
                            toDate = ZonedDateTime.ofInstant(
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
                            fromDate = ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(event.date),
                                ZoneId.systemDefault()
                            ),
                            remindIn = event.date - event.remindAt,
                            agendaType = AgendaType.EVENT,
                            screenMode = screenMode ?: ScreenMode.REVIEW,
                            photos = event.photos.map { it.uri },
                            attendees = event.attendees,
                            host = event.host,
                            toDate = ZonedDateTime.ofInstant(
                                Instant.ofEpochMilli(event.toDate),
                                ZoneId.systemDefault()
                            )
                        )
                    }
                }

                null -> Unit
            }
        }
    }

    fun onAction(agendaDetailAction: AgendaDetailAction) {
        when (agendaDetailAction) {
            is AgendaDetailAction.OnEditIconClick -> {
                state = state.copy(screenMode = ScreenMode.EDIT_ADD)
            }

            is AgendaDetailAction.OnSaveClick -> {
                viewModelScope.launch {
                    val dateMillis = state.fromDate.toInstant().toEpochMilli()
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
                                    attendees = state.attendees ?: listOf(),
                                    toDate = state.toDate.toInstant().toEpochMilli(),
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
                    toDate = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                            state.toDate.extractFromStartOfTheDayOfDateMillis() +
                                agendaDetailAction.millisOfHour +
                                agendaDetailAction.millisOfMinutes
                        ),
                        ZoneId.systemDefault()
                    ),
                )
            }

            is AgendaDetailAction.OnDateChanged -> {
                state = state.copy(
                    toDate = ZonedDateTime.ofInstant(
                        Instant.ofEpochMilli(
                            agendaDetailAction.millisOfDate.extractFromStartOfTheDayOfDateMillis() +
                                state.toDate.extractDayMillis()
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

            is AgendaDetailAction.OnEmailChanged -> {
                state = state.copy(
                    addingVisitorEmail = agendaDetailAction.email
                )
            }

            is AgendaDetailAction.OnEmailInputFocusChanged -> {
                if (agendaDetailAction.isFocused || state.addingVisitorEmail == String.EMPTY_STRING) {
                    state = state.copy(
                        isEmailChecked = false,
                        addingVisitorEmailErrMsg = null
                    )
                } else {
                    validateInputs()
                }
            }

            is AgendaDetailAction.OnAddVisitorButtonClicked -> {
                viewModelScope.launch {
                    eventRepository.checkAttendee(state.addingVisitorEmail).fold(
                        onError = {
                            val message = when (it) {
                                DataError.Network.BAD_REQUEST -> {
                                    UiText.StringResource(R.string.error_email_visitor_bad_request)
                                }

                                DataError.Network.NO_INTERNET -> {
                                    UiText.StringResource(R.string.error_no_internet)
                                }

                                else -> {
                                    UiText.StringResource(R.string.error_unknown)
                                }
                            }
                            _uiEvent.send(
                                AgendaDetailUiEvent.ShowSnackBar(
                                    message
                                )
                            )
                        },
                        onSuccess = { attendeeModel ->
                            if (attendeeModel == null) {
                                _uiEvent.send(
                                    AgendaDetailUiEvent.ShowSnackBar(
                                        UiText.StringResource(R.string.error_visitor_does_not_exist)
                                    )
                                )
                            } else {
                                state = state.copy(
                                    attendees = state.attendees?.plus(attendeeModel)
                                )
                                _uiEvent.send(
                                    AgendaDetailUiEvent.ShowSnackBar(
                                        UiText.StringResource(R.string.message_added_visitor)
                                    )
                                )
                            }
                        }
                    )
                    state = state.copy(isAddVisitorDialogShown = false)
                }
            }

            is AgendaDetailAction.OnAddVisitorIconClick -> {
                state = state.copy(isAddVisitorDialogShown = true)
            }

            is AgendaDetailAction.OnCloseAddVisitorDialogClick -> {
                state = state.copy(isAddVisitorDialogShown = false)
            }

            else -> Unit
        }
    }

    private fun validateInputs() {
        when (validateEmailUseCase(state.addingVisitorEmail)) {

            InputValidationError.EmailValidatorError.Missing -> {
                state =
                    state.copy(addingVisitorEmailErrMsg = UiText.StringResource(R.string.error_mandatory_field))
            }

            InputValidationError.EmailValidatorError.Format -> {
                state =
                    state.copy(addingVisitorEmailErrMsg = UiText.StringResource(R.string.error_email_wrong_format))
            }

            else -> {
                state =
                    state.copy(isEmailChecked = true)
            }
        }
    }
}