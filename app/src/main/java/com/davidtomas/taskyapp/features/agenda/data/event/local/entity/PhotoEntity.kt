package com.davidtomas.taskyapp.features.agenda.data.event.local.entity

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import io.realm.kotlin.types.RealmObject

class PhotoEntity : RealmObject {
    var key: String = String.EMPTY_STRING
    var url: String = String.EMPTY_STRING
}