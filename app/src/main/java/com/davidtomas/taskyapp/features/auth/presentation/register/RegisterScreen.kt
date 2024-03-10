package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.core.presentation.components.IconD
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.auth.presentation._common.components.BasicInput
import com.davidtomas.taskyapp.features.auth.presentation._common.components.InputPassword

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {

            RegisterHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer),
                onBackIconClick = {
                    onAction(RegisterAction.NavigateBackToLogin)
                }
            )
            Form(
                modifier = Modifier
                    .weight(3f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    )
                    .fillMaxWidth()
                    .background(Color.White),
                userName = state.fullName,
                onUserNameChanged = { userName ->
                    onAction(RegisterAction.OnUserNameChanged(userName))
                },
                onUserFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnUserNameFocusChanged(isFocus))
                },
                email = state.email,
                onEmailChanged = { email ->
                    onAction(RegisterAction.OnEmailChanged(email))
                },
                onEmailFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnEmailFocusChanged(isFocus))
                },
                password = state.password,
                onPasswordChanged = { password ->
                    onAction(RegisterAction.OnPasswordChanged(password))
                },
                onPasswordFocusChanged = { isFocus ->
                    onAction(RegisterAction.OnPasswordFocusChanged(isFocus))
                },
                userNameErrMsg = state.userNameErrMsg?.asString(),
                passwordErrMsg = state.passwordErrMsg?.asString(),
                emailErrMsg = state.emailErrMsg?.asString(),
                isPasswordVisible = state.isPasswordVisible,
                onVisibilityIconClicked = {
                    onAction(RegisterAction.OnChangePasswordVisibility)
                },
                onButtonClicked = {
                    onAction(RegisterAction.OnRegisterButtonClicked)
                },
                isUserNameChecked = state.isUserNameChecked,
                isEmailChecked = state.isEmailChanged
            )
        }
    }
}

@Composable
fun RegisterHeader(
    modifier: Modifier,
    onBackIconClick: () -> Unit
) {
    Box(
        modifier = modifier,
    ) {
        Header(
            leadingIcon = {
                IconD(
                    modifier = Modifier.clickable { onBackIconClick() },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(id = R.string.title_register)
        )
    }
}

@Suppress("LongParameterList")
@Composable
fun Form(
    modifier: Modifier,
    userName: String,
    onUserNameChanged: (String) -> Unit,
    onUserFocusChanged: (Boolean) -> Unit,
    email: String,
    onEmailChanged: (String) -> Unit,
    onEmailFocusChanged: (Boolean) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onPasswordFocusChanged: (Boolean) -> Unit,
    userNameErrMsg: String?,
    passwordErrMsg: String?,
    emailErrMsg: String?,
    isPasswordVisible: Boolean,
    onVisibilityIconClicked: () -> Unit,
    onButtonClicked: () -> Unit,
    isUserNameChecked: Boolean,
    isEmailChecked: Boolean,
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
    ) {
        BasicInput(
            label = stringResource(id = R.string.label_name),
            inputText = userName,
            onInputTextChanged = {
                onUserNameChanged(it)
            },
            isInputChecked = isUserNameChecked,
            errorMessage = userNameErrMsg,
            onInputFocusChanged = onUserFocusChanged
        )
        BasicInput(
            label = stringResource(id = R.string.label_email),
            inputText = email,
            onInputTextChanged = {
                onEmailChanged(it)
            },
            isInputChecked = isEmailChecked,
            errorMessage = emailErrMsg,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            onInputFocusChanged = onEmailFocusChanged
        )
        InputPassword(
            passwordText = password,
            onPasswordChanged = {
                onPasswordChanged(it)
            },
            onPasswordFocusChanged = onPasswordFocusChanged,
            errorMessage = passwordErrMsg,
            isVisible = isPasswordVisible,
            onVisibilityIconClick = onVisibilityIconClicked
        )
        Button(
            modifier = Modifier
                .padding(
                    start = spacing.spaceLarge,
                    top = spacing.spaceLarge,
                    end = spacing.spaceLarge
                )
                .fillMaxWidth(),
            onClick = onButtonClicked
        ) {
            Text(text = stringResource(id = R.string.btn_text_register))
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1080px,height=2400px",
    name = "Redmin Note 10 Pro"
)
@Composable
fun RegisterScreenPreview() {
    TaskyAppTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}
