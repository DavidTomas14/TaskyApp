package com.davidtomas.taskyapp.features.agenda.data.agenda.repository

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.map
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper.toEventModel
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper.toReminderModel
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.mapper.toTaskModel
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationScheduler
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.AgendaModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class AgendaRepositoryImpl(
    private val agendaRemoteSource: AgendaRemoteSource,
    private val eventLocalSource: EventLocalSource,
    private val reminderLocalSource: ReminderLocalSource,
    private val taskLocalSource: TaskLocalSource,
    private val dataStore: TaskyDataStore,
    private val notificationScheduler: NotificationScheduler,
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
            val userId = dataStore.getUserId()
            agendaRemoteSource.getAgenda().map {

                val saveEventsJob = launch(Dispatchers.IO) {
                    eventLocalSource.saveEvents(
                        it.eventResponses.map {
                            it.toEventModel(userId)
                        }
                    )
                }
                val scheduleEventsJob = launch {
                    it.eventResponses.map {
                        it.toEventModel(userId)
                    }.filter { it.remindAt > System.currentTimeMillis() }.forEach {
                        notificationScheduler.cancelScheduledNotificationAndPendingIntent(it)
                        notificationScheduler.scheduleNotification(it)
                    }
                }

                val saveTasksJob = launch {
                    taskLocalSource.saveTasks(
                        it.taskResponses.map {
                            it.toTaskModel()
                        }
                    )
                }

                val scheduleTasksJob = launch {
                    it.taskResponses.map {
                        it.toTaskModel()
                    }.filter { it.remindAt > System.currentTimeMillis() }.forEach {
                        notificationScheduler.cancelScheduledNotificationAndPendingIntent(it)
                        notificationScheduler.scheduleNotification(it)
                    }
                }
                val saveRemindersJob = launch {
                    reminderLocalSource.saveReminders(
                        it.reminderResponses.map {
                            it.toReminderModel()
                        }
                    )
                }

                val scheduleReminders = launch {
                    it.reminderResponses.map {
                        it.toReminderModel()
                    }.filter { it.remindAt > System.currentTimeMillis() }.forEach {
                        notificationScheduler.cancelScheduledNotificationAndPendingIntent(it)
                        notificationScheduler.scheduleNotification(it)
                    }
                }
                saveEventsJob.join()
                scheduleEventsJob.join()
                saveTasksJob.join()
                scheduleTasksJob.join()
                saveRemindersJob.join()
                scheduleReminders.join()
            }
        }
    }

    override suspend fun getAgenda(): List<AgendaModel> {
        return coroutineScope {
            val job1 = async(Dispatchers.IO) {
                eventLocalSource.getEvents()
            }
            val job2 = async {
                taskLocalSource.getTasks()
            }
            val job3 = async {
                reminderLocalSource.getReminders()
            }
            job1.await() + job2.await() + job3.await()
        }
    }

    override suspend fun clearTables() {
        eventLocalSource.clearRealmTables()
    }

    override suspend fun getFutureAgendaItems(): List<AgendaModel> {
        return coroutineScope {
            val job1 = async(Dispatchers.IO) {
                eventLocalSource.getFutureEvents()
            }
            val job2 = async {
                taskLocalSource.getFutureTasks()
            }
            val job3 = async {
                reminderLocalSource.getFutureReminders()
            }
            val agendaModelList = mutableListOf<AgendaModel>()
            agendaModelList.addAll(job1.await())
            agendaModelList.addAll(job2.await())
            agendaModelList.addAll(job3.await())
            agendaModelList
        }
    }
}