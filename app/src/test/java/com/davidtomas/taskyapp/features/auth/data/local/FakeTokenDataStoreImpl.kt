package com.davidtomas.taskyapp.features.auth.data.local

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTokenDataStoreImpl : TokenDataStore {

    private var token: String? = null
    override fun getToken(): Flow<String?> =
        flow { emit(token) }

    override suspend fun saveToken(token: String) {
        this.token = token
    }

    override suspend fun deleteToken() {
        this.token = String.EMPTY_STRING
    }
}