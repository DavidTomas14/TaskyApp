package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel

interface TaskRepository {
    suspend fun saveTask(taskModel: TaskModel, modificationType: ModificationType)
    suspend fun getTask(taskId: String): TaskModel
    suspend fun deleteTask(taskModel: TaskModel)
}