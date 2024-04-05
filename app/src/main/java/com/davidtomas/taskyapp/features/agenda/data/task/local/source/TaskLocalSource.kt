package com.davidtomas.taskyapp.features.agenda.data.task.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskLocalSource {
    suspend fun saveTask(task: TaskModel)

    suspend fun getTasks(): Flow<List<TaskModel>>
    suspend fun getTaskById(taskId: String): TaskModel

    suspend fun deleteTask(taskId: String)
}