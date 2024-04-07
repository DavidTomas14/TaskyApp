package com.davidtomas.taskyapp.features.agenda.data.agenda.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.map
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaService
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper.toEventModel
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper.toReminderModel
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper.toTaskModel
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AgendaRepositoryImpl(
    private val agendaService: AgendaService,
    private val eventLocalSource: EventLocalSource,
    private val reminderLocalSource: ReminderLocalSource,
    private val taskLocalSource: TaskLocalSource
) : AgendaRepository {
    override suspend fun observeAgenda(): Flow<List<AgendaModel>> =
        combine(
            eventLocalSource.getEvents(),
            taskLocalSource.getTasks(),
            reminderLocalSource.getReminder(),
        ) { events, tasks, reminder ->
            val listAgendaModel = mutableListOf<AgendaModel>()
            if (events.isNotEmpty()) listAgendaModel.addAll(events)
            if (tasks.isNotEmpty()) listAgendaModel.addAll(tasks)
            if (reminder.isNotEmpty()) listAgendaModel.addAll(reminder)
            listAgendaModel.toList()
        }

    override suspend fun deleteAgendaItem(agendaModel: AgendaModel) =
        when (agendaModel) {
            is EventModel -> eventLocalSource.deleteEvent(agendaModel.id)
            is ReminderModel -> reminderLocalSource.deleteReminder(agendaModel.id)
            is TaskModel -> taskLocalSource.deleteTask(agendaModel.id)
        }

    override suspend fun fetchAgenda(): Result<Unit, DataError.Network> {
        return coroutineScope {
            agendaService.getAgenda().map {
                val job1 = launch {
                    eventLocalSource.saveEvents(
                        it.eventResponses.map {
                            it.toEventModel()
                        }
                    )
                }
                val job2 = launch {
                    taskLocalSource.saveTasks(
                        it.taskResponses.map {
                            it.toTaskModel()
                        }
                    )
                }
                val job3 = launch {
                    reminderLocalSource.saveReminders(
                        it.reminderResponses.map {
                            it.toReminderModel()
                        }
                    )
                }
                job1.join()
                job2.join()
                job3.join()
            }
        }
    }
}