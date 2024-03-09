package com.davidtomas.taskyapp.features.auth.data.remote.mapper

import com.davidtomas.taskyapp.features.auth.data.remote.request.RegisterRequest
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase

fun RegisterUseCase.RegisterParams.toRegisterRequest() =
    RegisterRequest(fullName = fullName, email = email, password = password)