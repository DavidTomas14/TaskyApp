package com.davidtomas.taskyapp.features.auth.domain.model

sealed class InputValidationType

data class AllFieldsLoginInputValidationType(
    val email: String,
    val password: String
) : InputValidationType()

data class EmailInputValidationType(val email: String) : InputValidationType()
data class PasswordLoginInputValidationType(val password: String) : InputValidationType()
data class PasswordRegisterInputValidationType(val password: String) : InputValidationType()

data class AllFieldsRegisterValidationType(
    val userName: String,
    val email: String,
    val password: String
) : InputValidationType()

data class UserNameInputValidationType(val userName: String) : InputValidationType()
