package com.davidtomas.taskyapp.features.agenda.data.logout

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.logout.remote.api.LogoutService
import com.davidtomas.taskyapp.features.agenda.domain.repository.LogoutRepository
import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LogoutRepositoryImpl(
    private val logoutService: LogoutService,
    private val taskyDataStore: TaskyDataStore,
    private val appScope: CoroutineScope
) : LogoutRepository {

    override suspend fun logout(): Result<Unit, DataError.Network> {
        return logoutService.logout().also {
            appScope.launch {
                taskyDataStore.deleteToken()
            }
        }
    }
}