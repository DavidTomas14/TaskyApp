package com.davidtomas.taskyapp.features.agenda.di

import com.davidtomas.taskyapp.features.agenda.data._common.local.TaskyRealmDB
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaService
import com.davidtomas.taskyapp.features.agenda.data.agenda.remote.api.AgendaServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.agenda.repository.AgendaRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSource
import com.davidtomas.taskyapp.features.agenda.data.event.local.source.EventLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeService
import com.davidtomas.taskyapp.features.agenda.data.event.remote.api.AttendeeServiceImpl
import com.davidtomas.taskyapp.features.agenda.data.event.repository.EventRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSource
import com.davidtomas.taskyapp.features.agenda.data.photo.local.source.PhotoLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.photo.repository.PhotoRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSource
import com.davidtomas.taskyapp.features.agenda.data.reminder.local.source.ReminderLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.reminder.repository.ReminderRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSource
import com.davidtomas.taskyapp.features.agenda.data.task.local.source.TaskLocalSourceImpl
import com.davidtomas.taskyapp.features.agenda.data.task.repository.TaskRepositoryImpl
import com.davidtomas.taskyapp.features.agenda.domain.repository.AgendaRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.EventRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.PhotoRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.ReminderRepository
import com.davidtomas.taskyapp.features.agenda.domain.repository.TaskRepository
import com.davidtomas.taskyapp.features.agenda.domain.useCase.ObserveSelectedDayAgendaUseCase
import com.davidtomas.taskyapp.features.agenda.presentation.agenda.AgendaViewModel
import com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.AgendaDetailViewModel
import com.davidtomas.taskyapp.features.agenda.presentation.editText.EditTextViewModel
import com.davidtomas.taskyapp.features.agenda.presentation.photoDetail.PhotoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val agendaModule = module {
    dataModule()
    domainModule()
    presentationModule()
}

private fun Module.domainModule() {
    factoryOf(::ObserveSelectedDayAgendaUseCase)
}

private fun Module.dataModule() {
    single { TaskyRealmDB.create() }
    factoryOf(::AttendeeServiceImpl) bind AttendeeService::class
    factoryOf(::AttendeeServiceImpl) bind AttendeeService::class
    factoryOf(::AgendaRepositoryImpl) bind AgendaRepository::class
    factoryOf(::AgendaServiceImpl) bind AgendaService::class
    factoryOf(::EventRepositoryImpl) bind EventRepository::class
    factoryOf(::EventLocalSourceImpl) bind EventLocalSource::class
    factoryOf(::TaskRepositoryImpl) bind TaskRepository::class
    factoryOf(::TaskLocalSourceImpl) bind TaskLocalSource::class
    factoryOf(::ReminderRepositoryImpl) bind ReminderRepository::class
    factoryOf(::ReminderLocalSourceImpl) bind ReminderLocalSource::class
    factoryOf(::PhotoRepositoryImpl) bind PhotoRepository::class
    factoryOf(::PhotoLocalSourceImpl) bind PhotoLocalSource::class
}

private fun Module.presentationModule() {
    viewModelOf(::AgendaViewModel)
    viewModelOf(::AgendaDetailViewModel)
    viewModelOf(::EditTextViewModel)
    viewModelOf(::PhotoDetailViewModel)
}