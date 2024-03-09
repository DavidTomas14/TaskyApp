package com.davidtomas.taskyapp.features.auth.domain.model

sealed class InputValidationType {
    data class EmailInputValidationType(val email: String) : InputValidationType()
    data class PasswordInputValidationType(val password: String) : InputValidationType()
    data class UserNameInputValidationType(val userName: String) : InputValidationType()

    data class AllFieldsRegisterValidationType(
        val userName: String,
        val email: String,
        val password: String
    ) : InputValidationType()
}
