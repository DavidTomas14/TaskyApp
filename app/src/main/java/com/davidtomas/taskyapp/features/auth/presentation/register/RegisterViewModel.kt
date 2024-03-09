package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain.model.Failure
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationType
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase

class RegisterViewModel(
    private val validateRegistrationFieldsUseCase: ValidateRegistrationFieldsUseCase
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnChangePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }

            is RegisterAction.OnEmailChanged -> {
                state = state.copy(email = action.email)
            }

            is RegisterAction.OnPasswordChanged -> {
                state = state.copy(password = action.password)
            }

            is RegisterAction.OnUserNameChanged -> {
                state = state.copy(userName = action.userName)
            }

            is RegisterAction.OnEmailFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(emailErrMsg = null)
                } else {
                    validateInputs(InputValidationType.EmailInputValidationType(state.email))
                }
            }

            is RegisterAction.OnUserNameFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(userNameErrMsg = null)
                } else {
                    validateInputs(InputValidationType.UserNameInputValidationType(state.userName))
                }
            }

            is RegisterAction.OnPasswordFocusChanged -> {
                if (action.isFocus) {
                    state = state.copy(passwordErrMsg = null)
                }
            }

            is RegisterAction.OnRegisterButtonClicked -> {
                validateInputs(
                    InputValidationType.AllFieldsRegisterValidationType(
                        state.userName,
                        state.email,
                        state.password
                    )
                )
            }

            else -> Unit
        }
    }

    private fun validateInputs(
        inputValidationType: InputValidationType
    ) {
        validateRegistrationFieldsUseCase(inputValidationType).fold(
            onError = {
                onValidationError(it, inputValidationType)
            },
            onSuccess = {
                onValidationSuccess(inputValidationType)
            }
        )
    }

    private fun onValidationSuccess(inputValidationType: InputValidationType) {
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                state =
                    state.copy(isEmailChanged = true)

            is InputValidationType.UserNameInputValidationType ->
                state =
                    state.copy(isUserNameChecked = true)

            else -> {
                cleanErrors()
            }
        }
    }

    private fun onValidationError(failure: Failure, inputValidationType: InputValidationType) {
        val validationsErrors = (failure as InputValidationErrors).validationErrors
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                state =
                    state.copy(isEmailChanged = false)

            is InputValidationType.UserNameInputValidationType ->
                state =
                    state.copy(isUserNameChecked = false)

            else -> {
                cleanErrors()
                validationsErrors.forEach { inputValidationErrors ->
                    when (inputValidationErrors) {
                        InputValidationError.UserNameValidatorError.Missing -> {
                            state =
                                state.copy(userNameErrMsg = UiText.StringResource(R.string.error_mandatory_field))
                        }

                        InputValidationError.UserNameValidatorError.Length -> {
                            state =
                                state.copy(userNameErrMsg = UiText.StringResource(R.string.error_username_length))
                        }

                        InputValidationError.UserNameValidatorError.Format -> {
                            state =
                                state.copy(userNameErrMsg = UiText.StringResource(R.string.error_username_wrong_format))
                        }

                        InputValidationError.PasswordValidatorError.Missing -> {
                            state =
                                state.copy(passwordErrMsg = UiText.StringResource(R.string.error_mandatory_field))
                        }

                        InputValidationError.PasswordValidatorError.Length -> {
                            state =
                                state.copy(passwordErrMsg = UiText.StringResource(R.string.error_password_length))
                        }

                        InputValidationError.PasswordValidatorError.Format -> {
                            state =
                                state.copy(passwordErrMsg = UiText.StringResource(R.string.error_password_wrong_format))
                        }

                        InputValidationError.EmailValidatorError.Missing -> {
                            state =
                                state.copy(emailErrMsg = UiText.StringResource(R.string.error_mandatory_field))
                        }

                        InputValidationError.EmailValidatorError.Format -> {
                            state =
                                state.copy(emailErrMsg = UiText.StringResource(R.string.error_email_wrong_format))
                        }
                    }
                }
            }
        }
    }

    private fun cleanErrors() {
        state = state.copy(
            emailErrMsg = null,
            passwordErrMsg = null,
            userNameErrMsg = null,
        )
    }
}
