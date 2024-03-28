package com.davidtomas.taskyapp.features.agenda.data.event.local.entity

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class EventEntity : RealmObject {
    @PrimaryKey
    var id: String = ObjectId().toString()
    var title: String = String.EMPTY_STRING
    var description: String = String.EMPTY_STRING
    var from: Long = 0
    var to: Long = 0
    var remindAt: Long = 0
    var host: String = String.EMPTY_STRING
    var isUserEventCreator: Boolean = false
    var attendees: RealmList<AttendeeEntity> = realmListOf()
    var photos: RealmList<PhotoEntity> = realmListOf()
}