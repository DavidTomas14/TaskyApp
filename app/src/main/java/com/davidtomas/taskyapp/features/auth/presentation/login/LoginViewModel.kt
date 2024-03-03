package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain.model.Failure
import com.davidtomas.taskyapp.core.domain.util.EMPTY_STRING
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.auth.domain.model.AllFieldsLoginInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.EmailFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.EmailInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationType
import com.davidtomas.taskyapp.features.auth.domain.model.MissingMandatoryField
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordFormatError
import com.davidtomas.taskyapp.features.auth.domain.model.PasswordLoginInputValidationType
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateInputsUseCase

class LoginViewModel(
    private val validateInputsUseCase: ValidateInputsUseCase
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
                validateInputs(EmailInputValidationType(action.email))
            }

            is OnPasswordChanged -> {
                state = state.copy(password = action.password)
                validateInputs(PasswordLoginInputValidationType(action.password))
            }

            else -> Unit
        }
    }

    private fun validateInputs(
        inputValidationType: InputValidationType
    ) {
        validateInputsUseCase.invoke(inputValidationType).fold(
            onError = ::onValidationError,
            onSuccess = {
                onValidationSuccess(inputValidationType)
            }
        )
    }

    private fun onValidationSuccess(inputValidationType: InputValidationType) {
        when (inputValidationType) {
            is AllFieldsLoginInputValidationType -> state = state.copy(isLoginBtnEnabled = true)
            else -> {
                state = state.copy(
                    emailErrMsg = UiText.DynamicString(String.EMPTY_STRING),
                    passwordErrMsg = UiText.DynamicString(String.EMPTY_STRING)
                )
                validateInputs(AllFieldsLoginInputValidationType(state.email, state.password))
            }
        }
    }

    private fun onValidationError(failure: Failure) {
        state = state.copy(isLoginBtnEnabled = false)
        val validationsErrors = (failure as InputValidationErrors).validationErrors
        validationsErrors.forEach { loginInputValidationError ->
            when (loginInputValidationError) {
                EmailFormatError ->
                    state =
                        state.copy(emailErrMsg = UiText.StringResource(R.string.error_email_wrong_format))

                PasswordFormatError ->
                    state =
                        state.copy(passwordErrMsg = UiText.StringResource(R.string.error_password_wrong_format))

                MissingMandatoryField -> Unit
                else -> Unit
            }
        }
    }
}