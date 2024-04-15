package com.davidtomas.taskyapp.features.agenda.data.task.repository

import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskService
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskService: TaskService,
    private val taskLocalSource: TaskLocalSource
) : TaskRepository {
    override suspend fun saveTask(taskModel: TaskModel, modificationType: ModificationType) {
        when (modificationType) {
            ModificationType.EDIT -> taskService.updateTask(taskModel)
            else -> taskService.createTask(taskModel)
        }.fold(
            onSuccess = {
                taskLocalSource.saveTask(taskModel, ModificationType.NO_MODIFICATION)
            },
            onError = {
                taskLocalSource.saveTask(taskModel, modificationType)
            }
        )
    }

    override suspend fun getTask(taskId: String) =
        taskLocalSource.getTaskById(taskId)

    override suspend fun deleteTask(taskId: String) {
        taskService.deleteTask(taskId = taskId)
            .fold(
                onSuccess = {
                    taskLocalSource.deleteTask(taskId = taskId)
                },
                onError = {
                    taskLocalSource.deleteTask(taskId = taskId, ModificationType.DELETE)
                }
            )
    }
}