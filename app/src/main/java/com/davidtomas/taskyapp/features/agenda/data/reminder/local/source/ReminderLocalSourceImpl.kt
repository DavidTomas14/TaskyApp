package com.davidtomas.taskyapp.features.agenda.data.reminder.local.source

import com.davidtomas.taskyapp.features.agenda.data.reminder.local.entity.ReminderEntity
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.mapper.toReminderEntity
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.mapper.toReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderLocalSourceImpl(
    private val realmDb: Realm
) : ReminderLocalSource {
    override suspend fun saveReminder(event: ReminderModel) {
        realmDb.write {
            copyToRealm(event.toReminderEntity(), UpdatePolicy.ALL)
        }
    }

    override suspend fun getReminder(): Flow<List<ReminderModel>> = realmDb
        .query<ReminderEntity>()
        .asFlow()
        .map { results ->
            results.list.toList().map { it.toReminderModel() }
        }

    override suspend fun deleteReminder(event: ReminderModel) {
        realmDb.write {
            val latestEvent = findLatest(event.toReminderEntity()) ?: return@write
            delete(latestEvent)
        }
    }
}