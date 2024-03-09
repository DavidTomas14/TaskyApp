package com.davidtomas.taskyapp.features.auth.presentation.register

import com.davidtomas.taskyapp.core.domain.util.EMPTY_STRING
import com.davidtomas.taskyapp.core.presentation.util.UiText

data class RegisterState(
    val userName: String = String.EMPTY_STRING,
    val userNameErrMsg: UiText? = null,
    val email: String = String.EMPTY_STRING,
    val emailErrMsg: UiText? = null,
    val password: String = String.EMPTY_STRING,
    val passwordErrMsg: UiText? = null,
    val isPasswordVisible: Boolean = false,
    val isUserNameChecked: Boolean = false,
    val isEmailChanged: Boolean = false
)
