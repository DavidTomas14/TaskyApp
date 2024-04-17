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
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.Dispatchers
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
    override suspend fun observeAgendaByDate(startOfDayMillis: Long, endOfDateMillis: Long): Flow<List<AgendaModel>> =
        combine(
            eventLocalSource.getEventsByDate(startOfDayMillis, endOfDateMillis),
            taskLocalSource.getTasksByDate(startOfDayMillis, endOfDateMillis),
            reminderLocalSource.getReminderByDate(startOfDayMillis, endOfDateMillis),
        ) { events, tasks, reminder ->
            val listAgendaModel = mutableListOf<AgendaModel>()
            if (events.isNotEmpty()) listAgendaModel.addAll(events)
            if (tasks.isNotEmpty()) listAgendaModel.addAll(tasks)
            if (reminder.isNotEmpty()) listAgendaModel.addAll(reminder)
            listAgendaModel.toList()
        }

    override suspend fun fetchAgenda(): Result<Unit, DataError.Network> {
        return coroutineScope {
            agendaService.getAgenda().map {
                val job1 = this.launch(Dispatchers.IO) {
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