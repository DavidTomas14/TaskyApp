package com.davidtomas.taskyapp.features.agenda.data.task.remote.api

import com.davidtomas.taskyapp.core.data.util.safeRequest
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data._common.remote.AgendaPaths
import com.davidtomas.taskyapp.features.agenda.data.task.remote.mapper.toTaskRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.path

class TaskServiceImpl(
    private val client: HttpClient,
) : TaskService {

    override suspend fun createTask(task: TaskModel): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.TASK_ROUTE) }
            method = HttpMethod.Post
            setBody(task.toTaskRequest())
        }

    override suspend fun updateTask(task: TaskModel): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.TASK_ROUTE) }
            method = HttpMethod.Put
            setBody(task.toTaskRequest())
        }

    override suspend fun deleteTask(taskId: String): Result<Unit, DataError.Network> =
        client.safeRequest<Unit> {
            url { path(AgendaPaths.TASK_ROUTE) }
            method = HttpMethod.Delete
            parameter(AgendaPaths.TASK_ID_PARAM, taskId)
        }
}
