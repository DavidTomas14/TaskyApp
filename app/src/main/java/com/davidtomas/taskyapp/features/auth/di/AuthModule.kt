package com.davidtomas.taskyapp.features.auth.di

import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateEmailUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidatePasswordUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateUserNameUseCase
import com.davidtomas.taskyapp.features.auth.presentation.login.LoginViewModel
import com.davidtomas.taskyapp.features.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authModule = module {
    domainModule()
    presentationModule()
}

private fun Module.domainModule() {
    factoryOf(::ValidateEmailUseCase)
    factoryOf(::ValidatePasswordUseCase)
    factoryOf(::ValidateUserNameUseCase)
    factoryOf(::ValidateRegistrationFieldsUseCase)
}

private fun Module.presentationModule() {
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}
