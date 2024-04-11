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
    override suspend fun saveReminder(reminder: ReminderModel) {
        realmDb.write {
            copyToRealm(reminder.toReminderEntity(), UpdatePolicy.ALL)
        }
    }

    override suspend fun saveReminders(reminders: List<ReminderModel>) {
        realmDb.write {
            reminders.forEach { copyToRealm(it.toReminderEntity(), UpdatePolicy.ALL) }
        }
    }

    override suspend fun getReminderByDate(
        startOfDayMillis: Long,
        endOfDateMillis: Long
    ): Flow<List<ReminderModel>> = realmDb
        .query<ReminderEntity>("time > $0 && time < $1", startOfDayMillis, endOfDateMillis)
        .asFlow()
        .map { results ->
            results.list.toList().map { it.toReminderModel() }
        }

    override suspend fun getRemindById(reminderId: String): ReminderModel = realmDb
        .query<ReminderEntity>("id == $0", reminderId).find().first()
        .toReminderModel()

    override suspend fun deleteReminder(reminderId: String) {
        realmDb.write {
            val reminderToDelete = query<ReminderEntity>("id == $0", reminderId).find().first()
            delete(reminderToDelete)
        }
    }
}