package com.davidtomas.taskyapp.features.auth.presentation.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
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
fun InputPassword(
    value: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String = String.EMPTY_STRING,
) {
    val spacing = LocalSpacing.current
    val colors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
        focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
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
        supportingText = {
            if (isError){
                Text(
                    color = MaterialTheme.colorScheme.error,
                    text = errorMessage
                )
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = "")
        }
    )
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    TaskyAppTheme {
        InputPassword(value = "Password", onValueChanged = {}, isError = true, errorMessage = "Erroooor")
    }
}
