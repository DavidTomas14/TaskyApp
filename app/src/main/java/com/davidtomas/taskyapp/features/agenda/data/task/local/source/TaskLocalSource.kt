package com.davidtomas.taskyapp.features.agenda.data.task.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskLocalSource {
    suspend fun saveTask(event: TaskModel)

    suspend fun getTasks(): Flow<List<TaskModel>>
    suspend fun getTaskById(eventId: String): TaskModel

    suspend fun deleteTask(event: TaskModel)
}