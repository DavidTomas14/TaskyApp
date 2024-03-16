package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.core.domain._util.Result
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationType

class ValidateRegistrationFieldsUseCase(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validateUserNameUseCase: ValidateUserNameUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
) {

    operator fun invoke(
        inputValidationType: InputValidationType
    ): Result<Unit, InputValidationErrors> {
        val listOfErrors = mutableListOf<InputValidationError>()
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType -> {
                validateEmailUseCase(inputValidationType.email)?.let {
                    listOfErrors.add(it)
                }
            }

            is InputValidationType.UserNameInputValidationType -> {
                validateUserNameUseCase(inputValidationType.userName)?.let {
                    listOfErrors.add(it)
                }
            }

            is InputValidationType.PasswordInputValidationType -> {
                validatePasswordUseCase(inputValidationType.password)?.let {
                    listOfErrors.add(it)
                }
            }

            is InputValidationType.AllFieldsRegisterValidationType -> {
                validateUserNameUseCase(inputValidationType.userName)?.let {
                    listOfErrors.add(it)
                }
                validateEmailUseCase(inputValidationType.email)?.let {
                    listOfErrors.add(it)
                }
                validatePasswordUseCase(inputValidationType.password)?.let {
                    listOfErrors.add(it)
                }
            }
        }
        return if (listOfErrors.isEmpty()) {
            Result.Success(Unit)
        } else {
            Result.Error(InputValidationErrors(listOfErrors))
        }
    }
}
