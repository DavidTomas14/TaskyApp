package com.davidtomas.taskyapp.features.auth.presentation.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.core.util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun BasicInput(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    errorMessage: String = String.EMPTY_STRING,
) {
    val spacing = LocalSpacing.current
    val colors = TextFieldDefaults.colors(
        disabledTextColor = Color.DarkGray,
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        disabledLabelColor = Color.DarkGray,
        disabledPlaceholderColor = Color.DarkGray,
    )
    TextField(
        modifier = Modifier
            .padding(
                start = spacing.spaceLarge,
                top = spacing.spaceLarge,
                end = spacing.spaceLarge
            )
            .fillMaxWidth(),
        colors = colors,
        value = value,
        onValueChange = { onValueChanged(it) },
        isError = isError,
        label = {
            Text(
                text = label
            )
        },
        supportingText = {
            if (isError) {
                Text(
                    color = MaterialTheme.colorScheme.error,
                    text = errorMessage
                )
            }
        },
    )
}

@Preview
@Composable
fun InputBasicPreview() {
    TaskyAppTheme {
        BasicInput(
            value = "Password",
            onValueChanged = {},
            isError = true,
            errorMessage = "Erroooor",
            label = "Password"
        )
    }
}
