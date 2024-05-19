package com.davidtomas.taskyapp.features.agenda.data.logout

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain.model.DataError
import com.davidtomas.taskyapp.features.agenda.data.logout.remote.api.LogoutRemoteSource
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.LogoutRepository
import com.davidtomas.taskyapp.features.auth.data.local.TaskyDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LogoutRepositoryImpl(
    private val logoutRemoteSource: LogoutRemoteSource,
    private val taskyDataStore: TaskyDataStore,
    private val agendaRepository: AgendaRepository,
    private val appScope: CoroutineScope
) : LogoutRepository {

    override suspend fun logout(): Result<Unit, DataError.Network> {
        return logoutRemoteSource.logout().also {
            appScope.launch {
                taskyDataStore.deleteToken()
                agendaRepository.clearTables()
            }
        }
    }
}