package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.MissingMandatoryField
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordLengthError
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidatePasswordUseCase.ValidationType.REGISTER

class ValidatePasswordUseCase {
    operator fun invoke(password: String, validationType: ValidationType): InputValidationError? {

        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }

        return when {
            password.isEmpty() -> MissingMandatoryField
            password.length > MIN_PASSWORD_LENGTH && validationType == REGISTER -> PasswordLengthError
            hasLowerCase && hasUpperCase && hasDigit && validationType == REGISTER -> PasswordFormatError
            else -> null
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 9
    }

    enum class ValidationType {
        LOGIN, REGISTER
    }
}
