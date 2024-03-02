package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.features.auth.domain.model.LoginInputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.MissingMandatoryField
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordFormatError

class ValidatePasswordUseCase {
    operator fun invoke(password: String): LoginInputValidationError? =
        when {
            password.isEmpty() -> MissingMandatoryField
            !password.matches(PASSWORD_REGEX.toRegex()) -> PasswordFormatError
            else -> null
        }
    companion object {
        private const val MIN_PASSWORD_LENGTH = 9
        const val PASSWORD_REGEX =
            "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$ %^&*-]).{$MIN_PASSWORD_LENGTH,}\$"
    }
}