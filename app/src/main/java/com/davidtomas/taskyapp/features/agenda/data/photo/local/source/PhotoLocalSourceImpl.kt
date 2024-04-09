package com.davidtomas.taskyapp.features.agenda.data.photo.local.source

import com.davidtomas.taskyapp.features.agenda.data.event.local.entity.PhotoEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query

class PhotoLocalSourceImpl(
    private val realmDb: Realm
) : PhotoLocalSource {

    override suspend fun deletePhoto(photoUri: String) {
        realmDb.write {
            val photoToDelete = query<PhotoEntity>("uri == $0", photoUri).find().first()
            delete(photoToDelete)
        }
    }
}