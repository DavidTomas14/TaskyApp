package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.features.auth.domain.model.EmailFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.LoginInputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.MissingMandatoryField

class ValidateEmailUseCase {
    operator fun invoke(email: String): LoginInputValidationError? =
        when {
            email.isBlank() -> MissingMandatoryField
            !email.matches(EMAIL_REGEX.toRegex()) -> EmailFormatError
            else -> null
        }
    companion object {
        const val EMAIL_REGEX = "[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+"
    }
}