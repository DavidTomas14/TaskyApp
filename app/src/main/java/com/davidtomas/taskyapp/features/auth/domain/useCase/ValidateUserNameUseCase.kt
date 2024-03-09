package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError.UserNameValidatorError

class ValidateUserNameUseCase {
    operator fun invoke(userName: String): UserNameValidatorError? =
        when {
            userName.isBlank() -> UserNameValidatorError.Missing
            userName.length !in MIN_LENGTH..MAX_LENGTH -> UserNameValidatorError.Length
            !userName.matches(USER_NAME_REGEX.toRegex()) -> UserNameValidatorError.Format
            else -> null
        }

    companion object {
        private const val MIN_LENGTH = 4
        private const val MAX_LENGTH = 50
        const val USER_NAME_REGEX = "^[0-9a-zA-Z_-]+\$"
    }
}
