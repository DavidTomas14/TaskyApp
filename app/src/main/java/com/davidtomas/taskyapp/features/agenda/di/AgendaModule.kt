package com.davidtomas.taskyapp.features.agenda.di

import androidx.work.WorkManager
import com.davidtomas.taskyapp.features.agenda.data._common.local.TaskyRealmDB
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaRemoteSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.agenda.repository.AgendaRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeRemoteSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.EventRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.EventRemoteSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.repository.EventRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.logout.LogoutRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.logout.remote.api.LogoutRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.logout.remote.api.LogoutRemoteSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationScheduler
import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationSchedulerImpl
import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSource
import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.photo.repository.PhotoRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderRemoteSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.repository.ReminderRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.sync.remote.api.SyncRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.sync.repository.SyncRepository
import com.davidtomas.taskyapp.features.agenda.data.sync.worker.ScheduleSyncAgendaSchedulerImpl
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskRemoteSource
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskRemoteSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.task.repository.TaskRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.user.UserRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.domain.ScheduleSyncAgendaScheduler
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.LogoutRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.PhotoRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.UserRepository
import com.davidtomas.taskyapp.features.agenda.domain.useCase.ObserveSelectedDayAgendaUseCase
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaViewModel
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.AgendaDetailViewModel
import com.davidtomas.taskyapp.features.agenda.presentation.editText.EditTextViewModel
import com.davidtomas.taskyapp.features.agenda.presentation.photoDetail.PhotoDetailViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaModule = module {
    dataModule()
    domainModule()
    presentationModule()
}

private fun Module.domainModule() {
    singleOf(::ObserveSelectedDayAgendaUseCase)
}

private fun Module.dataModule() {
    single { WorkManager.getInstance(androidApplication()) }
    singleOf(::ScheduleSyncAgendaSchedulerImpl) bind ScheduleSyncAgendaScheduler::class
    single { TaskyRealmDB.create() }
    singleOf(::LogoutRepositoryImpl) bind LogoutRepository::class
    singleOf(::LogoutRemoteSourceImpl) bind LogoutRemoteSource::class
    singleOf(::AttendeeRemoteSourceImpl) bind AttendeeRemoteSource::class
    singleOf(::AttendeeRemoteSourceImpl) bind AttendeeRemoteSource::class
    singleOf(::AgendaRepositoryImpl) bind AgendaRepository::class
    singleOf(::AgendaRemoteSourceImpl) bind AgendaRemoteSource::class
    singleOf(::EventRepositoryImpl) bind EventRepository::class
    singleOf(::EventRemoteSourceImpl) bind EventRemoteSource::class
    singleOf(::EventLocalSourceImpl) bind EventLocalSource::class
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
    singleOf(::TaskLocalSourceImpl) bind TaskLocalSource::class
    singleOf(::TaskRemoteSourceImpl) bind TaskRemoteSource::class
    singleOf(::ReminderRepositoryImpl) bind ReminderRepository::class
    singleOf(::ReminderLocalSourceImpl) bind ReminderLocalSource::class
    singleOf(::ReminderRemoteSourceImpl) bind ReminderRemoteSource::class
    singleOf(::PhotoRepositoryImpl) bind PhotoRepository::class
    singleOf(::PhotoLocalSourceImpl) bind PhotoLocalSource::class
    singleOf(::NotificationSchedulerImpl) bind NotificationScheduler::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    singleOf(::SyncRepository)
    singleOf(::SyncRemoteSource)
}

private fun Module.presentationModule() {
    viewModelOf(::AgendaViewModel)
    viewModelOf(::AgendaDetailViewModel)
    viewModelOf(::EditTextViewModel)
    viewModelOf(::PhotoDetailViewModel)
}