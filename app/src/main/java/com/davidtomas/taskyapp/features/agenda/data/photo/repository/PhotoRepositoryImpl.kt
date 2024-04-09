package com.davidtomas.taskyapp.features.agenda.data.photo.repository

import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.repository.PhotoRepository

class PhotoRepositoryImpl(
    private val photoLocalSource: PhotoLocalSource
) : PhotoRepository {
    override suspend fun deletePhoto(photoUri: String) {
        photoLocalSource.deletePhoto(photoUri)
    }
}