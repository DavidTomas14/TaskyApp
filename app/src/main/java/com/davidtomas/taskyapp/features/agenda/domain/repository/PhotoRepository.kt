package com.davidtomas.taskyapp.features.agenda.domain.repository

interface PhotoRepository {
    suspend fun deletePhoto(photoUri: String)
}