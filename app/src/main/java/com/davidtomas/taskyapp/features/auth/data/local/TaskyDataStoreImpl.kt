package com.davidtomas.taskyapp.features.auth.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

class TaskyDataStoreImpl(
    private val context: Context
) : TaskyDataStore {
    override fun getToken(): Flow<String?> {
        return context.dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    override suspend fun getUserId(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }.first()
    }

    override suspend fun getUserFullName(): String? {
        return context.dataStore.data.map { preferences ->
            preferences[FULL_NAME_KEY]
        }.first()
    }

    override suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    override suspend fun saveFullName(fullName: String) {
        context.dataStore.edit { preferences ->
            preferences[FULL_NAME_KEY] = fullName
        }
    }

    override suspend fun saveUserId(userId: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    override suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    private companion object {
        private val TOKEN_KEY = stringPreferencesKey("jwt_token")
        private val FULL_NAME_KEY = stringPreferencesKey("full_name")
        private val USER_ID_KEY = stringPreferencesKey("user_id")
    }
}