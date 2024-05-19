package com.davidtomas.taskyapp.features.agenda.data.reminder.local.source

import com.davidtomas.taskyapp.features.agenda.data.reminder.local.entity.ReminderEntity
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.mapper.toReminderEntity
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.mapper.toReminderModel
import com.davidtomas.taskyapp.features.agenda.domain.model.ModificationType
import com.davidtomas.taskyapp.features.agenda.domain.model.ReminderModel
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderLocalSourceImpl(
    private val realmDb: Realm
) : ReminderLocalSource {
    override suspend fun saveReminder(reminder: ReminderModel, modificationType: ModificationType?) {
        realmDb.write {
            copyToRealm(reminder.toReminderEntity(modificationType), UpdatePolicy.ALL)
        }
    }

    override suspend fun saveReminders(reminders: List<ReminderModel>) {
        realmDb.write {
            reminders.forEach {
                copyToRealm(
                    it.toReminderEntity(),
                    UpdatePolicy.ALL
                )
            }
        }
    }

    override suspend fun getReminderByDate(
        startOfDayMillis: Long,
        endOfDateMillis: Long
    ): Flow<List<ReminderModel>> = realmDb
        .query<ReminderEntity>("time > $0 && time < $1", startOfDayMillis, endOfDateMillis)
        .asFlow()
        .map { results ->
            results.list.toList()
                .filter { it.syncType?.let { it1 -> ModificationType.valueOf(it1) } != ModificationType.DELETE }
                .map { it.toReminderModel() }
        }

    override suspend fun getRemindById(reminderId: String): ReminderModel = realmDb
        .query<ReminderEntity>("id == $0", reminderId).find().first()
        .toReminderModel()

    override suspend fun getFutureReminders(): List<ReminderModel> = realmDb
        .query<ReminderEntity>("remindAt > $0", System.currentTimeMillis())
        .find()
        .map { it.toReminderModel() }

    override suspend fun getReminders(): List<ReminderModel> = realmDb
        .query<ReminderEntity>()
        .find()
        .map { it.toReminderModel() }

    override suspend fun deleteReminder(reminderId: String, modificationType: ModificationType?) {
        realmDb.write {
            val reminderToDelete = query<ReminderEntity>("id == $0", reminderId).find().first()
            modificationType?.let {
                copyToRealm(
                    reminderToDelete.apply {
                        this.syncType = it.name
                    },
                    UpdatePolicy.ALL
                )
            } ?: run {
                delete(reminderToDelete)
            }
        }
    }

    override suspend fun getUnsyncedDeletedReminder(): List<String> = realmDb
        .query<ReminderEntity>("syncType == $0", ModificationType.DELETE.name).find().map {
            it.id
        }

    override suspend fun getUnsyncedCreatedReminder(): List<ReminderModel> = realmDb
        .query<ReminderEntity>("syncType == $0", ModificationType.ADD.name).find().map {
            it.toReminderModel()
        }

    override suspend fun getUnsyncedUpdatedReminder(): List<ReminderModel> = realmDb
        .query<ReminderEntity>("syncType == $0", ModificationType.EDIT.name).find().map {
            it.toReminderModel()
        }
}