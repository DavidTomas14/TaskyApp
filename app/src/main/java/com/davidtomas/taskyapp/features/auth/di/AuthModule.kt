package com.davidtomas.taskyapp.features.auth.di

import com.davidtomas.taskyapp.core.data.remote.api.TaskyHttpClient
import com.davidtomas.taskyapp.features.auth.data.local.TokenDataStore
import com.davidtomas.taskyapp.features.auth.data.remote.AuthRepositoryImpl
import com.davidtomas.taskyapp.features.auth.data.remote.api.AuthService
import com.davidtomas.taskyapp.features.auth.data.remote.api.AuthServiceImpl
import com.davidtomas.taskyapp.features.auth.domain.AuthRepository
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateEmailUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidatePasswordUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateUserNameUseCase
import com.davidtomas.taskyapp.features.auth.presentation.login.LoginViewModel
import com.davidtomas.taskyapp.features.auth.presentation.register.RegisterViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    dataModule()
    domainModule()
    presentationModule()
}

private fun Module.dataModule() {
    factory<HttpClient> {
        TaskyHttpClient.create(get())
    }
    singleOf(::AuthServiceImpl) bind AuthService::class
    singleOf(::TokenDataStore)
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}

private fun Module.domainModule() {
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateUserNameUseCase)
    factoryOf(::ValidateRegistrationFieldsUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::RegisterUseCase)
}

private fun Module.presentationModule() {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
