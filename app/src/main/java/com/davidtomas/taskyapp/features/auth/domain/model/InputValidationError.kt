package com.davidtomas.taskyapp.features.auth.domain.model

import com.davidtomas.taskyapp.core.domain.model.Failure

sealed class InputValidationError : Failure
data object MissingMandatoryField : InputValidationError()
data object EmailFormatError : InputValidationError()
data object PasswordFormatError : InputValidationError()
data object PasswordLengthError : InputValidationError()
data object UserNameFormatError : InputValidationError()
data object UserNameLengthError : InputValidationError()

data class InputValidationErrors(
    val validationErrors: Set<InputValidationError>
) : Failure
