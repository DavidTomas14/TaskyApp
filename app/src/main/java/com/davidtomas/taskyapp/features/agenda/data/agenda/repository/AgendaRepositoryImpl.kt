package com.davidtomas.taskyapp.features.agenda.data.agenda.repository

import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.model.EventModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class AgendaRepositoryImpl(
    private val eventLocalSource: EventLocalSource,
    private val reminderLocalSource: ReminderLocalSource,
    private val taskLocalSource: TaskLocalSource
) : AgendaRepository {
    override suspend fun observeAgenda(): Flow<List<AgendaModel>> =
        /*coroutineScope {
            val jobEvents = async { eventLocalSource.getEvents() }
            val jobTasks = async { taskLocalSource.getTasks() }
            val jobReminders = async { reminderLocalSource.getReminder() }
            val eventsResult = jobEvents.await()
            val tasksResult = jobTasks.await()
            val remindersResult = jobReminders.await()
            val eventsFlow = eventsResult.getOrNull() ?: flow { listOf<AgendaModel>() }
            val tasksFlow = tasksResult.getOrNull() ?: flow { listOf<AgendaModel>() }
            val remindersFlow = remindersResult.getOrNull() ?: flow { listOf<AgendaModel>() }
            val listAgendaModel = mutableListOf<AgendaModel>()*/
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
            is EventModel -> eventLocalSource.deleteEvent(agendaModel)
            is ReminderModel -> reminderLocalSource.deleteReminder(agendaModel)
            is TaskModel -> taskLocalSource.deleteTask(agendaModel)
        }
}