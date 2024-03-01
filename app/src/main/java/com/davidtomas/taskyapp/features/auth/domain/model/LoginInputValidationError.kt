package com.davidtomas.taskyapp.features.auth.domain.model

import com.davidtomas.taskyapp.core.domain.util.Failure

sealed class LoginInputValidationError : Failure
data object MissingMandatoryField : LoginInputValidationError()
data object EmailFormatError : LoginInputValidationError()
data object PasswordFormatError : LoginInputValidationError()

data class LoginInputValidationErrors(
    val validationErrors: Set<LoginInputValidationError>
) : Failure
