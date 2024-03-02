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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.core.domain.util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun BasicInput(
    value: String,
    onValueChanged: (String) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    errorMessage: String = String.EMPTY_STRING,
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
            .fillMaxWidth(),
        colors = colors,
        value = value,
        onValueChange = { onValueChanged(it) },
        isError = errorMessage.isNotEmpty(),
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
            if (errorMessage.isNotEmpty()) {
                Text(
                    color = MaterialTheme.colorScheme.error,
                    text = errorMessage
                )
            }
        },
        trailingIcon = {
            if (errorMessage.isEmpty() && value.isNotEmpty())
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
                value = "",
                onValueChanged = {},
                errorMessage = "Error",
                label = "Email"
            )
            BasicInput(
                value = "dtalonso@gmail.com",
                onValueChanged = {},
                label = "Email"
            )
        }
    }
}
