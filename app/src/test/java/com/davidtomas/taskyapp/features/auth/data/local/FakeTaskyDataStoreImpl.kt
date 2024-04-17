package com.davidtomas.taskyapp.features.auth.data.local

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskyDataStoreImpl : TaskyDataStore {

    private var token: String? = null
    private var userId: String? = null
    private var fullName: String? = null
    override fun getToken(): Flow<String?> =
        flow { emit(token) }

    override suspend fun saveToken(token: String) {
        this.token = token
    }

    override suspend fun saveFullName(fullName: String) {
        this.fullName = fullName
    }

    override suspend fun saveUserId(userId: String) {
        this.userId = userId
    }

    override suspend fun deleteToken() {
        this.token = String.EMPTY_STRING
    }
}