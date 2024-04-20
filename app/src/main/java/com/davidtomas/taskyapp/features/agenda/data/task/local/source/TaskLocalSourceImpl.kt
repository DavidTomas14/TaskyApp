package com.davidtomas.taskyapp.features.agenda.data.task.local.source

import com.davidtomas.taskyapp.features.agenda.data.task.local.entity.TaskEntity
import com.davidtomas.taskyapp.features.agenda.data.task.local.mapper.toTaskEntity
import com.davidtomas.taskyapp.features.agenda.data.task.local.mapper.toTaskModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.TaskModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskLocalSourceImpl(
    private val realmDb: Realm
) : TaskLocalSource {
    override suspend fun saveTask(task: TaskModel, modificationType: ModificationType?) {
        realmDb.write {
            copyToRealm(task.toTaskEntity(modType = modificationType), UpdatePolicy.ALL)
        }
    }

    override suspend fun saveTasks(tasks: List<TaskModel>) {
        realmDb.write {
            tasks.forEach {
                copyToRealm(
                    it.toTaskEntity(),
                    UpdatePolicy.ALL
                )
            }
        }
    }

    override suspend fun getTasksByDate(startOfDayMillis: Long, endOfDateMillis: Long): Flow<List<TaskModel>> = realmDb
        .query<TaskEntity>("time > $0 && time < $1", startOfDayMillis, endOfDateMillis)
        .asFlow()
        .map { results ->
            results.list.toList()
                .filter { it.syncType?.let { it1 -> ModificationType.valueOf(it1) } != ModificationType.DELETE }
                .map { it.toTaskModel() }
        }

    override suspend fun getTaskById(taskId: String): TaskModel = realmDb
        .query<TaskEntity>("id == $0", taskId).find().first()
        .toTaskModel()

    override suspend fun getFutureTasks(): List<TaskModel> = realmDb
        .query<TaskEntity>("remindAt > $0", System.currentTimeMillis())
        .find()
        .map { it.toTaskModel() }

    override suspend fun deleteTask(taskId: String, modificationType: ModificationType?) {
        realmDb.write {
            val taskToDelete = query<TaskEntity>("id == $0", taskId).find().first()
            modificationType?.let {
                copyToRealm(
                    taskToDelete.apply {
                        this.syncType = it.name
                    },
                    UpdatePolicy.ALL
                )
            } ?: run {
                delete(taskToDelete)
            }
        }
    }
}