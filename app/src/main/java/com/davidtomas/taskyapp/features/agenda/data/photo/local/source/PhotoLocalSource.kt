package com.davidtomas.taskyapp.features.agenda.data.photo.local.source

interface PhotoLocalSource {
    suspend fun deletePhoto(photoUri: String)
}