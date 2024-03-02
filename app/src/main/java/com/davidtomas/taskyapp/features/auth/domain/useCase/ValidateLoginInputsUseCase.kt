package com.davidtomas.taskyapp.features.auth.domain.useCase

import com.davidtomas.taskyapp.core.domain.model.Failure
import com.davidtomas.taskyapp.core.domain.model.Result
import com.davidtomas.taskyapp.features.auth.domain.model.AllFieldsLoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.EmailLoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.LoginInputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.LoginInputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.LoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordLoginValidationType

class ValidateLoginInputsUseCase(
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) {

    private val loginInputValidationError = mutableSetOf<LoginInputValidationError>()

    operator fun invoke(
        loginValidationType: LoginValidationType
    ): Result<Unit, Failure> {
        loginInputValidationError.clear()
        when (loginValidationType) {
            is EmailLoginValidationType -> {
                validateEmailUseCase(loginValidationType.email)?.let {
                    loginInputValidationError.add(it)
                }
            }

            is PasswordLoginValidationType -> {
                validatePasswordUseCase(loginValidationType.password)?.let {
                    loginInputValidationError.add(it)
                }
            }

            is AllFieldsLoginValidationType -> {
                validateEmailUseCase(loginValidationType.email)?.let {
                    loginInputValidationError.add(it)
                }
                validatePasswordUseCase(loginValidationType.password)?.let {
                    loginInputValidationError.add(it)
                }
            }
        }
        return if (loginInputValidationError.isEmpty()) {
            Result.Success(Unit)
        } else {
            Result.Error(LoginInputValidationErrors(loginInputValidationError))
        }
    }
}