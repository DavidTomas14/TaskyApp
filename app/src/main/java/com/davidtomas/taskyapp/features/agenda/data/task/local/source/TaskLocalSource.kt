package com.davidtomas.taskyapp.features.agenda.data.task.local.source

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskLocalSource {
    suspend fun saveTask(event: TaskModel)

    suspend fun getTasks(): Result<Flow<List<TaskModel>>, DataError>

    suspend fun deleteTask(event: TaskModel)
}