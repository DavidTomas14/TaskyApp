package com.davidtomas.taskyapp.features.agenda.di

import com.davidtomas.taskyapp.features.agenda.data._common.local.TaskyRealmDB
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaService
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.agenda.repository.AgendaRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeService
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.EventService
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.EventServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.repository.EventRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.logout.LogoutRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.logout.remote.api.LogoutService
import com.davidtomas.taskyapp.features.agenda.data.logout.remote.api.LogoutServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationScheduler
import com.davidtomas.taskyapp.features.agenda.data.notifications.NotificationSchedulerImpl
import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSource
import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.photo.repository.PhotoRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderService
import com.davidtomas.taskyapp.features.agenda.data.reminder.remote.api.ReminderServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.repository.ReminderRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskService
import com.davidtomas.taskyapp.features.agenda.data.task.remote.api.TaskServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.task.repository.TaskRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.user.UserRepositoryImpl
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
    single { TaskyRealmDB.create() }
    singleOf(::LogoutRepositoryImpl) bind LogoutRepository::class
    singleOf(::LogoutServiceImpl) bind LogoutService::class
    singleOf(::AttendeeServiceImpl) bind AttendeeService::class
    singleOf(::AttendeeServiceImpl) bind AttendeeService::class
    singleOf(::AgendaRepositoryImpl) bind AgendaRepository::class
    singleOf(::AgendaServiceImpl) bind AgendaService::class
    singleOf(::EventRepositoryImpl) bind EventRepository::class
    singleOf(::EventServiceImpl) bind EventService::class
    singleOf(::EventLocalSourceImpl) bind EventLocalSource::class
    singleOf(::TaskRepositoryImpl) bind TaskRepository::class
    singleOf(::TaskLocalSourceImpl) bind TaskLocalSource::class
    singleOf(::TaskServiceImpl) bind TaskService::class
    singleOf(::ReminderRepositoryImpl) bind ReminderRepository::class
    singleOf(::ReminderLocalSourceImpl) bind ReminderLocalSource::class
    singleOf(::ReminderServiceImpl) bind ReminderService::class
    singleOf(::PhotoRepositoryImpl) bind PhotoRepository::class
    singleOf(::PhotoLocalSourceImpl) bind PhotoLocalSource::class
    singleOf(::NotificationSchedulerImpl) bind NotificationScheduler::class
    singleOf(::UserRepositoryImpl) bind UserRepository::class
}

private fun Module.presentationModule() {
    viewModelOf(::AgendaViewModel)
    viewModelOf(::AgendaDetailViewModel)
    viewModelOf(::EditTextViewModel)
    viewModelOf(::PhotoDetailViewModel)
}