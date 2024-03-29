package com.davidtomas.taskyapp.features.auth.data.remote.mapper

import com.davidtomas.taskyapp.features.auth.data.remote.request.LoginRequest
import com.davidtomas.taskyapp.features.auth.data.remote.response.LoginResponse
import com.davidtomas.taskyapp.features.auth.domain.model.AuthModel
import com.davidtomas.taskyapp.features.auth.domain.useCase.LoginUseCase

fun LoginResponse.toAuthModel() =
    AuthModel(token = token.orEmpty(), userId = userId.orEmpty(), fullName = fullName.orEmpty())

fun LoginUseCase.LoginParams.toLoginRequest() =
    LoginRequest(email = email, password = password)