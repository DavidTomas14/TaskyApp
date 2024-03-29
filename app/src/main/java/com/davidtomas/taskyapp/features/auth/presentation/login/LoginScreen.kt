package com.davidtomas.taskyapp.features.auth.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.auth.presentation._common.components.BasicInput
import com.davidtomas.taskyapp.features.auth.presentation._common.components.InputPassword

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {
            Header(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer),
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
                email = state.email,
                onEmailChanged = { email ->
                    onAction(LoginAction.OnEmailChanged(email))
                },
                password = state.password,
                onPasswordChanged = { password ->
                    onAction(LoginAction.OnPasswordChanged(password))
                },
                isPasswordVisible = state.isPasswordVisible,
                onVisibilityIconClicked = {
                    onAction(LoginAction.OnChangePasswordVisibility)
                },
                onLoginButtonClicked = {
                    onAction(LoginAction.OnLoginButtonClick)
                }
            )
        }
        Footer(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.spaceLarge),
            onGoToRegister = {
                onAction(LoginAction.NavigateToRegisterButtonClick)
            }
        )
    }
}

@Composable
fun Header(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(id = R.string.title_log_in)
        )
    }
}

@Composable
fun Form(
    modifier: Modifier,
    email: String,
    onEmailChanged: (String) -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    isPasswordVisible: Boolean,
    onVisibilityIconClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
    ) {
        BasicInput(
            label = stringResource(id = R.string.label_email),
            inputText = email,
            onInputTextChanged = { onEmailChanged(it) },
        )
        InputPassword(
            passwordText = password,
            onPasswordChanged = { onPasswordChanged(it) },
            isVisible = isPasswordVisible,
            onVisibilityIconClick = onVisibilityIconClicked,
        )
        Button(
            modifier = Modifier
                .padding(
                    start = spacing.spaceLarge,
                    top = spacing.spaceLarge,
                    end = spacing.spaceLarge
                )
                .fillMaxWidth(),
            onClick = {
                onLoginButtonClicked()
            },
        ) {
            Text(text = stringResource(id = R.string.btn_text_log_in))
        }
    }
}

@Composable
fun Footer(
    modifier: Modifier,
    onGoToRegister: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = stringResource(id = R.string.txt_dont_have_an_account)
        )
        Text(
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            text = stringResource(id = R.string.txt_sign_up),
            modifier = Modifier
                .clickable {
                    onGoToRegister()
                }
                .padding(start = spacing.spaceTiny)
        )
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1080px,height=2400px",
    name = "Redmin Note 10 Pro"
)
@Composable
fun LoginPreview() {
    TaskyAppTheme {
        LoginScreen(LoginState(), {})
    }
}
