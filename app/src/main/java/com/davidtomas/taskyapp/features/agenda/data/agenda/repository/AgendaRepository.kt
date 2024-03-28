package com.davidtomas.taskyapp.features.agenda.data.agenda.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.getOrNull
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class AgendaRepository(
    private val eventLocalSource: EventLocalSource,
    private val reminderLocalSource: ReminderLocalSource,
    private val taskLocalSource: TaskLocalSource
) {
    suspend fun getAgenda(): Result<Flow<List<AgendaItem>>, DataError.Network> =
        coroutineScope {
            val jobEvents = async { eventLocalSource.getEvents() }
            val jobTasks = async { taskLocalSource.getTasks() }
            val jobReminders = async { reminderLocalSource.getReminder() }
            val eventsResult = jobEvents.await()
            val tasksResult = jobTasks.await()
            val remindersResult = jobReminders.await()
            val eventsFlow = eventsResult.getOrNull() ?: flow { listOf<AgendaItem>() }
            val tasksFlow = tasksResult.getOrNull() ?: flow { listOf<AgendaItem>() }
            val remindersFlow = remindersResult.getOrNull() ?: flow { listOf<AgendaItem>() }
            val listAgendaItem = mutableListOf<AgendaItem>()
            Result.Success(
                combine(
                    eventsFlow,
                    tasksFlow,
                    remindersFlow
                ) { events, tasks, reminder ->
                    if (events.isNotEmpty()) listAgendaItem.addAll(events)
                    if (tasks.isNotEmpty()) listAgendaItem.addAll(tasks)
                    if (reminder.isNotEmpty()) listAgendaItem.addAll(reminder)
                    listAgendaItem.toList()
                }
            )
        }
}