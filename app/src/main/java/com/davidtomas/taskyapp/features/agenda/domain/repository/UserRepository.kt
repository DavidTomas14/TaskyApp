package com.davidtomas.taskyapp.features.agenda.domain.repository

import com.davidtomas.taskyapp.features.agenda.domain.model.User

interface UserRepository {
    suspend fun getUserInfo(): User
}