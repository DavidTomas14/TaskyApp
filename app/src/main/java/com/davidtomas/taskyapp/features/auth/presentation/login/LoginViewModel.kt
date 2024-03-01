package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.davidtomas.taskyapp.core.domain.util.EMPTY_STRING
import com.davidtomas.taskyapp.core.domain.util.Failure
import com.davidtomas.taskyapp.features.auth.domain.model.AllFieldsLoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.EmailFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.EmailLoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.LoginInputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.LoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.MissingMandatoryField
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordLoginValidationType
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateLoginInputsUseCase

class LoginViewModel(
    private val validateLoginInputsUseCase: ValidateLoginInputsUseCase
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onAction(action: LoginAction) {
        when (action) {
            is OnChangePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is OnEmailChanged -> {
                state = state.copy(email = action.email)
                validateInputs(EmailLoginValidationType(action.email))
            }

            is OnPasswordChanged -> {
                state = state.copy(password = action.password)
                validateInputs(PasswordLoginValidationType(action.password))
            }

            else -> Unit
        }
    }

    private fun validateInputs(
        loginValidationType: LoginValidationType
    ) {
        validateLoginInputsUseCase.invoke(loginValidationType).fold(
            fnL = ::onValidationError,
            fnR = {
                onValidationSuccess(loginValidationType)
            }
        )
    }

    private fun onValidationSuccess(loginValidationType: LoginValidationType) {
        when (loginValidationType) {
            is AllFieldsLoginValidationType -> state = state.copy(isLoginBtnEnabled = true)
            else -> {
                state = state.copy(
                    emailErrMsg = String.EMPTY_STRING,
                    passwordErrMsg = String.EMPTY_STRING
                )
                validateInputs(AllFieldsLoginValidationType(state.email, state.password))
            }
        }
    }

    private fun onValidationError(failure: Failure) {
        state = state.copy(isLoginBtnEnabled = false)
        val validationsErrors = (failure as LoginInputValidationErrors).validationErrors
        validationsErrors.forEach { loginInputValidationError ->
            when (loginInputValidationError) {
                EmailFormatError -> state =
                    state.copy(emailErrMsg = "The format of the email is wrong")

                PasswordFormatError -> state =
                    state.copy(passwordErrMsg = "The format of the password is wrong")

                MissingMandatoryField -> Unit
            }
        }
    }
}


