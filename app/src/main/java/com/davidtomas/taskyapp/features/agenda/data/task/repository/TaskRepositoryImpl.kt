package com.davidtomas.taskyapp.features.agenda.data.task.repository

import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskLocalSource: TaskLocalSource
) : TaskRepository {
    override suspend fun saveTask(taskModel: TaskModel) =
        taskLocalSource.saveTask(taskModel)
}