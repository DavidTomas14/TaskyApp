package com.davidtomas.taskyapp.features.agenda.data._common.local

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.AttendeeEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.EventEntity
import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.PhotoEntity
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.entity.ReminderEntity
import com.davidtomas.taskyapp.features.agenda.data.task.local.entity.TaskEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

object TaskyRealmDB {
    fun create(): Realm {
        val config = RealmConfiguration.create(
            schema = setOf(
                EventEntity::class,
                AttendeeEntity::class,
                PhotoEntity::class,
                ReminderEntity::class,
                TaskEntity::class
            )
        )
        return Realm.open(config)
    }
}