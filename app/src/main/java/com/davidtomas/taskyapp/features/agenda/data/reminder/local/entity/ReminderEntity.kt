package com.davidtomas.taskyapp.features.agenda.data.reminder.local.entity

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class ReminderEntity : RealmObject {
    @PrimaryKey
    var id: String = String.EMPTY_STRING
    var title: String = String.EMPTY_STRING
    var description: String = String.EMPTY_STRING
    var time: Long = 0
    var remindAt: Long = 0
    var syncType: String? = null
}