package com.davidtomas.taskyapp.features.auth.presentation.login

import com.davidtomas.taskyapp.core.domain.util.EMPTY_STRING

data class LoginState(
    val email: String = String.EMPTY_STRING,
    val emailErrMsg: String = String.EMPTY_STRING,
    val password: String = String.EMPTY_STRING,
    val passwordErrMsg: String = String.EMPTY_STRING,
    val isPasswordVisible: Boolean = false,
    val isLoginBtnEnabled: Boolean = false
)