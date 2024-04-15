package com.davidtomas.taskyapp.features.agenda.data.task.remote.mapper

import com.davidtomas.taskyapp.features.agenda.data.task.remote.request.TaskRequest
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel

fun TaskModel.toTaskRequest() =
    TaskRequest(
        id = this.id,
        title = this.title,
        description = this.description,
        time = this.date,
        remindAt = this.remindAt,
        isDone = this.isDone
    )