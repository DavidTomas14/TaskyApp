package com.davidtomas.taskyapp.features.auth.common.di

import com.davidtomas.taskyapp.features.auth.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::LoginViewModel)
}
