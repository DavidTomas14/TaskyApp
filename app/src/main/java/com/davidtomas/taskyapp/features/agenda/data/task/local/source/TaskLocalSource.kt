package com.davidtomas.taskyapp.features.agenda.data.task.local.source

import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskLocalSource {
    suspend fun saveTask(task: TaskModel, modificationType: ModificationType? = null)

    suspend fun saveTasks(tasks: List<TaskModel>)

    suspend fun getTasksByDate(startOfDayMillis: Long, endOfDateMillis: Long): Flow<List<TaskModel>>
    suspend fun getTaskById(taskId: String): TaskModel
    suspend fun getFutureTasks(): List<TaskModel>
    suspend fun getTasks(): List<TaskModel>

    suspend fun deleteTask(
        taskId: String,
        modificationType: ModificationType? = null
    )
}