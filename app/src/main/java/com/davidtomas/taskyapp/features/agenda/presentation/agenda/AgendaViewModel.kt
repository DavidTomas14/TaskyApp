package com.davidtomas.taskyapp.features.agenda.presentation.agenda

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.core.presentation.util.daysOfWeekIncludingGivenDate
import com.davidtomas.taskyapp.features.agenda.domain.ScheduleSyncAgendaScheduler
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.LogoutRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.UserRepository
import com.davidtomas.taskyapp.features.agenda.domain.useCase.ObserveSelectedDayAgendaUseCase
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.components.AgendaUiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

class AgendaViewModel(
    private val agendaRepository: AgendaRepository,
    private val observeSelectedDayAgendaUseCase: ObserveSelectedDayAgendaUseCase,
    private val logoutRepository: LogoutRepository,
    private val taskRepository: TaskRepository,
    private val reminderRepository: ReminderRepository,
    private val eventRepository: EventRepository,
    private val scheduleSyncAgendaSchedulerImpl: ScheduleSyncAgendaScheduler,
    private val scheduleSyncAgendaScheduler: ScheduleSyncAgendaScheduler,
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(AgendaState())
        private set

    private val _uiEvent = Channel<AgendaUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _selectedDate = MutableStateFlow(ZonedDateTime.now())
    private val selectedDate: StateFlow<ZonedDateTime> = _selectedDate

    init {
        viewModelScope.launch {
            scheduleSyncAgendaScheduler.schedule()
            val getUserJob = launch {
                val user = userRepository.getUserInfo()
                state = state.copy(
                    userFullName = user.fullName
                )
            }
            val checkCachedDataJob = launch {
                if (agendaRepository.getAgenda().isEmpty()) {
                    agendaRepository.fetchAgenda().fold(
                        onError = {},
                        onSuccess = {}
                    )
                }
            }
            val observeAgendaJob = launch {
                observeSelectedDayAgendaUseCase(selectedDate).collectLatest {
                    state = state.copy(agendaItems = it)
                }
            }
            getUserJob.join()
            checkCachedDataJob.join()
            observeAgendaJob.join()
        }
    }

    fun onAction(agendaAction: AgendaAction) {
        when (agendaAction) {
            is AgendaAction.OnDoneBulletClicked -> {
                viewModelScope.launch {
                    taskRepository.saveTask(
                        taskModel = agendaAction.taskModel.copy(
                            isDone = !agendaAction.taskModel.isDone
                        ),
                        modificationType = ModificationType.EDIT
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
                    when (agendaAction.agendaModel) {
                        is EventModel -> eventRepository.deleteEvent(agendaAction.agendaModel)
                        is ReminderModel -> reminderRepository.deleteReminder(agendaAction.agendaModel)
                        is TaskModel -> taskRepository.deleteTask(agendaAction.agendaModel)
                    }
                }
            }

            is AgendaAction.OnInitialsIconClicked -> {
                viewModelScope.launch {
                    logoutRepository.logout().fold(
                        onError = {},
                        onSuccess = {
                            scheduleSyncAgendaSchedulerImpl.cancelWork()
                            _uiEvent.send(AgendaUiEvent.NavigateToLogin)
                        }
                    )
                }
            }

            else -> Unit
        }
    }
}