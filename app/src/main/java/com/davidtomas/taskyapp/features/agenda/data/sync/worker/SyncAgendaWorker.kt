package com.davidtomas.taskyapp.features.agenda.data.sync.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.core.domain._util.Result.Error
import com.davidtomas.taskyapp.core.domain._util.Result.Success
import com.davidtomas.taskyapp.features.agenda.data.sync.repository.SyncRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
class SyncAgendaWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params), KoinComponent {

    private val syncRepository: SyncRepository by inject()
    override suspend fun doWork(): Result {
        Log.d("WorkerStatus", "Worker has started")

        return try {
            when (syncRepository()) {
                is Error -> {
                    Log.d("WorkerStatus", "Worker has finished with an error")
                    Result.retry()
                }

                is Success -> {
                    Log.d("WorkerStatus", "Worker has completed successfully")
                    Result.success()
                }
            }
        } catch (e: Exception) {
            Log.e("WorkerStatus", "Worker failure: ${e.message}")
            Result.retry()
        }
    }

    companion object {
        const val SYNC_AGENDA_WORKER = "SyncAgendaWorker"
    }
}
