package com.davidtomas.taskyapp.features.auth.presentation.login

import com.davidtomas.taskyapp.core.domain.util.EMPTY_STRING
import com.davidtomas.taskyapp.core.presentation.util.UiText

data class LoginState(
    val email: String = String.EMPTY_STRING,
    val emailErrMsg: UiText = UiText.DynamicString(String.EMPTY_STRING),
    val password: String = String.EMPTY_STRING,
    val passwordErrMsg: UiText = UiText.DynamicString(String.EMPTY_STRING),
    val isPasswordVisible: Boolean = false,
    val isLoginBtnEnabled: Boolean = false
)