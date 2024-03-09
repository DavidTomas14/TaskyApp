package com.davidtomas.taskyapp.features.auth.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun BasicInput(
    inputText: String,
    onInputTextChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onInputFocusChanged: ((Boolean) -> Unit)? = null,
    errorMessage: String? = null,
    isInputChecked: Boolean = false,
    leadingIcon: ImageVector? = null
) {
    val spacing = LocalSpacing.current
    val successColor = Color(0xFF279F70)
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

    OutlinedTextField(
        modifier = Modifier
            .padding(
                start = spacing.spaceLarge,
                top = spacing.spaceLarge,
                end = spacing.spaceLarge
            )
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                onInputFocusChanged?.let { it(focusState.isFocused) }
            },
        colors = colors,
        value = inputText,
        onValueChange = { onInputTextChanged(it) },
        isError = !errorMessage.isNullOrBlank(),
        keyboardOptions = keyboardOptions,
        label = {
            Text(
                text = label
            )
        },
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    tint = successColor,
                    contentDescription = ""
                )
            }
        } else null,
        supportingText = {
            if (!errorMessage.isNullOrBlank()) {
                Text(
                    color = MaterialTheme.colorScheme.error,
                    text = errorMessage
                )
            }
        },
        trailingIcon = {
            if (errorMessage.isNullOrBlank() && isInputChecked)
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    tint = successColor,
                    contentDescription = ""
                )
        },
        singleLine = true,
        maxLines = 1,
        minLines = 1,
    )
}

@Preview
@Composable
fun InputBasicPreview() {
    TaskyAppTheme {
        Column {
            BasicInput(
                inputText = "",
                onInputTextChanged = {},
                errorMessage = "Error",
                label = "Email"
            )
            BasicInput(
                inputText = "dtalonso@gmail.com",
                onInputTextChanged = {},
                label = "Email"
            )
        }
    }
}
