package com.davidtomas.taskyapp.features.agenda.data.user

import com.davidtomas.taskyapp.features.agenda.domain.model.User
import com.davidtomas.taskyapp.features.agenda.domain.repository.UserRepository
import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStore

class UserRepositoryImpl(
    private val taskyDataStore: TaskyDataStore
) : UserRepository {
    override suspend fun getUserInfo(): User =
        User(
            userId = taskyDataStore.getUserId().orEmpty(),
            fullName = taskyDataStore.getUserFullName().orEmpty()
        )
}