package com.davidtomas.taskyapp.features.auth.data.local

import kotlinx.coroutines.flow.Flow

interface TaskyDataStore {
    fun getToken(): Flow<String?>

    suspend fun saveToken(token: String)
    suspend fun saveFullName(fullName: String)
    suspend fun saveUserId(userId: String)

    suspend fun deleteToken()
}