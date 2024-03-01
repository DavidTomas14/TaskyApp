package com.davidtomas.taskyapp.features.auth.domain.model

sealed class LoginValidationType

data class AllFieldsLoginValidationType(
    val email: String,
    val password: String
) : LoginValidationType()

data class EmailLoginValidationType(val email: String) : LoginValidationType()
data class PasswordLoginValidationType(val password: String) : LoginValidationType()