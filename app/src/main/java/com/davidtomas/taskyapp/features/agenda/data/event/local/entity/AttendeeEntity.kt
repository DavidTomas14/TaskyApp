package com.davidtomas.taskyapp.features.agenda.data.event.local.entity

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import io.realm.kotlin.types.RealmObject

class AttendeeEntity : RealmObject {
    var email: String = String.EMPTY_STRING
    var fullName: String = String.EMPTY_STRING
    var eventId: String = String.EMPTY_STRING
    var isGoing: Boolean = false
    var remindAt: Long = 0
}