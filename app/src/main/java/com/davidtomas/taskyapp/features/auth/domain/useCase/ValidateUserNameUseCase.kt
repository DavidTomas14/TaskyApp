package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.MissingMandatoryField
import com.davidtomas.taskyapp.features.auth.domain.model.UserNameFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.UserNameLengthError

class ValidateUserNameUseCase {
    operator fun invoke(userName: String): InputValidationError? =
        when {
            userName.isBlank() -> MissingMandatoryField
            userName.length !in MIN_LENGTH..MAX_LENGTH -> UserNameLengthError
            !userName.matches(USER_NAME_REGEX.toRegex()) -> UserNameFormatError
            else -> null
        }

    companion object {
        private const val MIN_LENGTH = 4
        private const val MAX_LENGTH = 50
        const val USER_NAME_REGEX = "^[0-9a-zA-Z_-]+\$"
    }
}
