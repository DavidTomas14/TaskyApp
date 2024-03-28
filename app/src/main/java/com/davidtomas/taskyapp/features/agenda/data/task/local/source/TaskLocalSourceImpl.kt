package com.davidtomas.taskyapp.features.agenda.data.task.local.source

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.task.local.entity.TaskEntity
import com.davidtomas.taskyapp.features.agenda.data.task.local.mapper.toTaskEntity
import com.davidtomas.taskyapp.features.agenda.data.task.local.mapper.toTaskModel
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalSourceImpl(
    private val realmDb: Realm
) : TaskLocalSource {
    override suspend fun saveTask(event: TaskModel) {
        realmDb.write {
            copyToRealm(event.toTaskEntity(), UpdatePolicy.ALL)
        }
    }

    override suspend fun getTasks(): Result<Flow<List<TaskModel>>, DataError> {
        return try {
            val events = realmDb
                .query<TaskEntity>()
                .asFlow()
                .map { results ->
                    results.list.toList().map { it.toTaskModel() }
                }
            Result.Success(events)
        } catch (e: Exception) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteTask(event: TaskModel) {
        realmDb.write {
            val latestEvent = findLatest(event.toTaskEntity()) ?: return@write
            delete(latestEvent)
        }
    }
}