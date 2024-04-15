package com.davidtomas.taskyapp.features.agenda.data.task.local.mapper

import com.davidtomas.taskyapp.features.agenda.data.task.local.entity.TaskEntity
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel

fun TaskEntity.toTaskModel() =
    TaskModel(
        id = this.id,
        title = this.title,
        description = this.description,
        date = this.time,
        remindAt = this.remindAt,
        isDone = this.isDone
    )

fun TaskModel.toTaskEntity(modType: ModificationType? = null) =
    TaskEntity().apply {
        id = this@toTaskEntity.id
        title = this@toTaskEntity.title
        description = this@toTaskEntity.description
        time = this@toTaskEntity.date
        remindAt = this@toTaskEntity.remindAt
        isDone = this@toTaskEntity.isDone
        modificationType = modType?.name
        isSynced = if (modType != null) false else null
    }