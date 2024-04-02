package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel

interface TaskRepository {
    suspend fun saveTask(taskModel: TaskModel)
    suspend fun getTask(taskId: String): TaskModel
}