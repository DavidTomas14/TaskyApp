package com.davidtomas.taskyapp.features.agenda.data.task.repository

import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationScheduler
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskRemoteSource
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskRemoteSource: TaskRemoteSource,
    private val taskLocalSource: TaskLocalSource,
    private val notificationScheduler: NotificationScheduler,
) : TaskRepository {
    override suspend fun saveTask(taskModel: TaskModel, modificationType: ModificationType) {
        when (modificationType) {
            ModificationType.EDIT -> taskRemoteSource.updateTask(taskModel)
            else -> taskRemoteSource.createTask(taskModel)
        }.fold(
            onSuccess = {
                taskLocalSource.saveTask(taskModel)
                notificationScheduler.cancelScheduledNotificationAndPendingIntent(taskModel)
                notificationScheduler.scheduleNotification(taskModel)
            },
            onError = {
                taskLocalSource.saveTask(taskModel, modificationType)
            }
        )
    }

    override suspend fun getTask(taskId: String) =
        taskLocalSource.getTaskById(taskId)

    override suspend fun deleteTask(taskModel: TaskModel) {
        taskRemoteSource.deleteTask(taskId = taskModel.id)
            .fold(
                onSuccess = {
                    taskLocalSource.deleteTask(taskId = taskModel.id)
                    notificationScheduler.cancelScheduledNotificationAndPendingIntent(taskModel)
                },
                onError = {
                    taskLocalSource.deleteTask(taskId = taskModel.id, ModificationType.DELETE)
                }
            )
    }
}