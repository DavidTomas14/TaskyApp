package com.davidtomas.taskyapp.features.agenda.data.sync.repository

import android.util.Log
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.sync.remote.api.SyncRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskRemoteSource
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SyncRepository(
    private val syncRemoteSource: SyncRemoteSource,
    private val taskRemoteSource: TaskRemoteSource,
    private val reminderRemoteSource: ReminderRemoteSource,
    private val eventLocalSource: EventLocalSource,
    private val reminderLocalSource: ReminderLocalSource,
    private val taskLocalSource: TaskLocalSource,
    private val agendaRepository: AgendaRepository,
) {

    suspend operator fun invoke(): Result<Unit, DataError> {
        val deleteAgendaItems = deleteAgendaItems()
        if (deleteAgendaItems is Result.Error) return deleteAgendaItems
        val updateTasksResult = updateTasks()
        if (updateTasksResult is Result.Error) return updateTasksResult
        val updateRemindersResult = updateReminders()
        if (updateRemindersResult is Result.Error) return updateRemindersResult
        clearTaskyTables()
        val fetchAgendaResult = agendaRepository.fetchAgenda()
        if (fetchAgendaResult is Result.Error) return fetchAgendaResult
        return Result.Success(Unit)
    }

    private suspend fun clearTaskyTables() {
        eventLocalSource.clearRealTables()
    }

    private suspend fun deleteAgendaItems(): Result<Unit, DataError.Network> {
        val deletedEventsIds = mutableListOf<String>()
        val deletedTasksIds = mutableListOf<String>()
        val deletedRemindersIds = mutableListOf<String>()
        return coroutineScope {
            val getDeletedEvents = launch {
                deletedEventsIds.addAll(eventLocalSource.getUnsyncedDeletedEvents())
            }
            val getDeletedTasks = launch {
                deletedTasksIds.addAll(taskLocalSource.getUnsyncedDeletedTasks())
            }
            val getDeletedReminders = launch {
                deletedRemindersIds.addAll(reminderLocalSource.getUnsyncedDeletedReminder())
            }

            getDeletedEvents.join()
            getDeletedTasks.join()
            getDeletedReminders.join()
            Log.d("WorkerStatus", "$deletedEventsIds $deletedRemindersIds $deletedTasksIds")
            syncRemoteSource.syncAgendaItems(
                deletedEventsIds = deletedEventsIds,
                deletedTasksIds = deletedTasksIds,
                deletedRemindersIds = deletedRemindersIds
            )
        }
    }

    private suspend fun updateTasks(): Result<Unit, DataError.Network> {
        return coroutineScope {
            val updatedUnsyncedTasks = taskLocalSource.getUnsyncedUpdatedTasks()
            val updatedJobs = updatedUnsyncedTasks.map { taskId ->
                async {
                    taskRemoteSource.updateTask(taskId)
                }
            }

            val createdUnsyncedTasks = taskLocalSource.getUnsyncedCreatedTasks()

            val createdJobs = createdUnsyncedTasks.map { taskId ->
                async {
                    taskRemoteSource.createTask(taskId)
                }
            }
            (createdJobs + updatedJobs).awaitAll().map {
                if (it is Result.Error) return@coroutineScope it
            }
            return@coroutineScope Result.Success(Unit)
        }
    }

    private suspend fun updateReminders(): Result<Unit, DataError.Network> {
        return coroutineScope {
            val updatedUnsyncedReminders = reminderLocalSource.getUnsyncedUpdatedReminder()
            val updatedJobs = updatedUnsyncedReminders.map { reminderId ->
                async {
                    reminderRemoteSource.updateReminder(reminderId)
                }
            }

            val createdUnsyncedReminders = reminderLocalSource.getUnsyncedCreatedReminder()

            val createdJobs = createdUnsyncedReminders.map { reminderId ->
                async {
                    reminderRemoteSource.createReminder(reminderId)
                }
            }
            (createdJobs + updatedJobs).awaitAll().map {
                if (it is Result.Error) return@coroutineScope it
            }
            return@coroutineScope Result.Success(Unit)
        }
    }
}