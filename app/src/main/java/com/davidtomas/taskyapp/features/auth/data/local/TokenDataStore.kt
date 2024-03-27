package com.davidtomas.taskyapp.features.auth.data.local

import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    fun getToken(): Flow<String?>

    suspend fun saveToken(token: String)

    suspend fun deleteToken()
}