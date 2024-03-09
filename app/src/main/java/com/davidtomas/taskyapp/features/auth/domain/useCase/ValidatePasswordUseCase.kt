package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError.PasswordValidatorError

class ValidatePasswordUseCase {
    operator fun invoke(password: String): PasswordValidatorError? {

        val hasLowerCase = password.any { it.isLowerCase() }
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }

        return when {
            password.isEmpty() -> PasswordValidatorError.Missing
            password.length < MIN_PASSWORD_LENGTH -> PasswordValidatorError.Length
            !(hasLowerCase && hasUpperCase && hasDigit) -> PasswordValidatorError.Format
            else -> null
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 9
    }
}
