package com.davidtomas.taskyapp.features.agenda.data.task.remote.api

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel

interface TaskRemoteSource {

    suspend fun createTask(task: TaskModel): Result<Unit, DataError.Network>
    suspend fun updateTask(task: TaskModel): Result<Unit, DataError.Network>
    suspend fun deleteTask(taskId: String): Result<Unit, DataError.Network>
}