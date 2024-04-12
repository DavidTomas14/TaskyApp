package com.davidtomas.taskyapp.features.agenda.data.task.local.source

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
    override suspend fun saveTask(task: TaskModel) {
        realmDb.write {
            copyToRealm(task.toTaskEntity(), UpdatePolicy.ALL)
        }
    }

    override suspend fun saveTasks(tasks: List<TaskModel>) {
        realmDb.write {
            tasks.forEach { copyToRealm(it.toTaskEntity(), UpdatePolicy.ALL) }
        }
    }

    override suspend fun getTasks(): Flow<List<TaskModel>> = realmDb
        .query<TaskEntity>()
        .asFlow()
        .map { results ->
            results.list.toList().map { it.toTaskModel() }
        }

    override suspend fun getTaskById(taskId: String): TaskModel = realmDb
        .query<TaskEntity>("id == $0", taskId).find().first()
        .toTaskModel()

    override suspend fun deleteTask(taskId: String) {
        realmDb.write {
            val taskToDelete = query<TaskEntity>("id == $0", taskId).find().first()
            delete(taskToDelete)
        }
    }
}