package com.davidtomas.taskyapp.features.auth.presentation.login

import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING

data class LoginState(
    val email: String = String.EMPTY_STRING,
    val password: String = String.EMPTY_STRING,
    val isPasswordVisible: Boolean = false,
)
