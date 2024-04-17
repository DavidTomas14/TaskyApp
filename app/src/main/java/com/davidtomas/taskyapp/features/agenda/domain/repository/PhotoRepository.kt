package com.davidtomas.taskyapp.features.agenda.domain.repository

interface PhotoRepository {

    suspend fun getImageData(key: String): ByteArray?
    suspend fun saveImageData(key: String, data: ByteArray)
    suspend fun deletePhoto(photoUri: String)
}