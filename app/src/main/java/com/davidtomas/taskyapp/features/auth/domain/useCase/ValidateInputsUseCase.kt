package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.core.domain.model.Result
import com.davidtomas.taskyapp.features.auth.domain.model.AllFieldsLoginInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.AllFieldsRegisterValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.EmailInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordLoginInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordRegisterInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.UserNameInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidatePasswordUseCase.ValidationType.LOGIN
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidatePasswordUseCase.ValidationType.REGISTER

class ValidateInputsUseCase(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {

    private val inputValidationError = mutableSetOf<InputValidationError>()

    operator fun invoke(
        inputValidationType: InputValidationType
    ): Result<Unit, InputValidationErrors> {
        inputValidationError.clear()
        when (inputValidationType) {
            is EmailInputValidationType -> {
                validateEmailUseCase(inputValidationType.email)?.let {
                    inputValidationError.add(it)
                }
            }

            is UserNameInputValidationType -> {
                validateUserNameUseCase(inputValidationType.userName)?.let {
                    inputValidationError.add(it)
                }
            }

            is PasswordLoginInputValidationType -> {
                validatePasswordUseCase(inputValidationType.password, LOGIN)?.let {
                    inputValidationError.add(it)
                }
            }

            is PasswordRegisterInputValidationType -> {
                validatePasswordUseCase(inputValidationType.password, REGISTER)?.let {
                    inputValidationError.add(it)
                }
            }

            is AllFieldsLoginInputValidationType -> {
                validateEmailUseCase(inputValidationType.email)?.let {
                    inputValidationError.add(it)
                }
                validatePasswordUseCase.invoke(inputValidationType.password, LOGIN)?.let {
                    inputValidationError.add(it)
                }
            }

            is AllFieldsRegisterValidationType -> {
                validateUserNameUseCase(inputValidationType.userName)?.let {
                    inputValidationError.add(it)
                }
                validateEmailUseCase(inputValidationType.email)?.let {
                    inputValidationError.add(it)
                }
                validatePasswordUseCase(inputValidationType.password, REGISTER)?.let {
                    inputValidationError.add(it)
                }
            }
        }
        return if (inputValidationError.isEmpty()) {
            Result.Success(Unit)
        } else {
            Result.Error(InputValidationErrors(inputValidationError))
        }
    }
}
