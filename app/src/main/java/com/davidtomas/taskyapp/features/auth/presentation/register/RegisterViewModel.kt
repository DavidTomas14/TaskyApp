package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain._util.Error
import com.davidtomas.taskyapp.core.presentation.util.UiText
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationError
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationErrors
import com.davidtomas.taskyapp.features.auth.domain.model.InputValidationType
import com.davidtomas.taskyapp.features.auth.domain.useCase.RegisterUseCase
import com.davidtomas.taskyapp.features.auth.domain.useCase.ValidateRegistrationFieldsUseCase
import com.davidtomas.taskyapp.features.auth.presentation._common.mapper.toStringResource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val validateRegistrationFieldsUseCase: ValidateRegistrationFieldsUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    var state by mutableStateOf(RegisterState())
        private set

    private val _uiEvent = Channel<RegisterUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                state = state.copy(fullName = action.userName)
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
                    validateInputs(InputValidationType.UserNameInputValidationType(state.fullName))
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
                        state.fullName,
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
            }
        ) {
            onValidationSuccess(inputValidationType)
        }
    }

    private fun onValidationSuccess(inputValidationType: InputValidationType) {
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                state =
                    state.copy(isEmailChecked = true)

            is InputValidationType.UserNameInputValidationType ->
                state =
                    state.copy(isUserNameChecked = true)

            else -> {
                cleanErrors()
                register()
            }
        }
    }

    private fun onValidationError(failure: Error?, inputValidationType: InputValidationType) {
        val validationsErrors = (failure as InputValidationErrors).validationErrors
        when (inputValidationType) {
            is InputValidationType.EmailInputValidationType ->
                state =
                    state.copy(isEmailChecked = false)

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

    private fun register() {
        viewModelScope.launch {
            registerUseCase(
                RegisterUseCase.RegisterParams(
                    fullName = state.fullName,
                    email = state.email,
                    password = state.password
                )
            ).fold(
                onError = { dataError ->
                    _uiEvent.send(
                        RegisterUiEvent.ShowSnackBar(
                            UiText.StringResource(
                                resId = dataError.toStringResource()
                            )
                        )
                    )
                }
            ) {
                _uiEvent.send(
                    RegisterUiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.register_success
                        )
                    )
                )
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
