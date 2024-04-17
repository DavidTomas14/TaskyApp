package com.davidtomas.taskyapp.features.agenda.data.photo.repository

import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSource
import com.davidtomas.taskyapp.features.agenda.domain.repository.PhotoRepository

class PhotoRepositoryImpl(
    private val photoLocalSource: PhotoLocalSource
) : PhotoRepository {

    private val imageDataStore = mutableMapOf<String, ByteArray>()

    override suspend fun saveImageData(key: String, data: ByteArray) {
        imageDataStore.clear()
        imageDataStore[key] = data
    }

    override suspend fun getImageData(key: String): ByteArray? {
        return imageDataStore[key]
    }
    override suspend fun deletePhoto(photoUri: String) {
        photoLocalSource.deletePhoto(photoUri)
    }
}