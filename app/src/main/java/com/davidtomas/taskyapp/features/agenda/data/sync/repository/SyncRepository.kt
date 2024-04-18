package com.davidtomas.taskyapp.features.agenda.data.sync.repository

import android.util.Log
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderService
import com.davidtomas.taskyapp.features.agenda.data.sync.remote.api.SyncService
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskService
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SyncRepository(
    private val syncService: SyncService,
    private val taskService: TaskService,
    private val reminderService: ReminderService,
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
                deletedEventsIds.addAll(eventLocalSource.getUnSyncedDeletedEvents())
            }
            val getDeletedTasks = launch {
                deletedTasksIds.addAll(taskLocalSource.getUnSyncedDeletedTasks())
            }
            val getDeletedReminders = launch {
                deletedRemindersIds.addAll(reminderLocalSource.getUnSyncedDeletedReminder())
            }

            getDeletedEvents.join()
            getDeletedTasks.join()
            getDeletedReminders.join()
            Log.d("WorkerStatus", "$deletedEventsIds $deletedRemindersIds $deletedTasksIds")
            syncService.syncAgendaItems(
                deletedEventsIds = deletedEventsIds,
                deletedTasksIds = deletedTasksIds,
                deletedRemindersIds = deletedRemindersIds
            )
        }
    }

    private suspend fun updateTasks(): Result<Unit, DataError.Network> {
        val updatedUnSyncedTasks = taskLocalSource.getUnSyncedUpdatedTasks()
        Log.d("WorkerStatus", "$updatedUnSyncedTasks")
        if (updatedUnSyncedTasks.isNotEmpty()) {
            updatedUnSyncedTasks.map {
                val result = taskService.updateTask(it)
                if (result is Result.Error) return result
            }
        }
        val createdUnSyncedTasks = taskLocalSource.getUnSyncedCreatedTasks()
        Log.d("WorkerStatus", "$createdUnSyncedTasks")
        if (createdUnSyncedTasks.isNotEmpty()) {
            createdUnSyncedTasks.map {
                val result = taskService.createTask(it)
                if (result is Result.Error) return result
            }
        }
        return Result.Success(Unit)
    }

    private suspend fun updateReminders(): Result<Unit, DataError.Network> {
        val updatedUnSyncedReminders = reminderLocalSource.getUnSyncedUpdatedReminder()
        Log.d("WorkerStatus", "$updatedUnSyncedReminders")
        if (updatedUnSyncedReminders.isNotEmpty()) {
            updatedUnSyncedReminders.map {
                val result = reminderService.updateReminder(it)
                if (result is Result.Error) return result
            }
        }

        val createdUnSyncedReminders = reminderLocalSource.getUnSyncedCreatedReminder()
        Log.d("WorkerStatus", "$createdUnSyncedReminders")
        if (createdUnSyncedReminders.isNotEmpty()) {
            createdUnSyncedReminders.map {
                val result = reminderService.createReminder(it)
                if (result is Result.Error) return result
            }
        }
        return Result.Success(Unit)
    }
}