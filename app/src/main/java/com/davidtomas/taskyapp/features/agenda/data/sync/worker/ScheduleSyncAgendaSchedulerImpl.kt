package com.davidtomas.taskyapp.features.agenda.data.sync.worker

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.davidtomas.taskyapp.features.agenda.domain.ScheduleSyncAgendaScheduler
import java.util.concurrent.TimeUnit

class ScheduleSyncAgendaSchedulerImpl(
    private val workManager: WorkManager
) : ScheduleSyncAgendaScheduler {

    override fun schedule() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncAgendaWorkRequest = PeriodicWorkRequestBuilder<SyncAgendaWorker>(
            repeatInterval = 15,
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .addTag("TAG_OUTPUT")
            .setConstraints(constraints)
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            /* uniqueWorkName = */ SyncAgendaWorker.SYNC_AGENDA_WORKER,
            /* existingPeriodicWorkPolicy = */ ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            /* periodicWork = */ syncAgendaWorkRequest
        )
    }

    override fun cancelWork() {
        workManager.cancelAllWork()
    }
}