package com.davidtomas.taskyapp.features.auth.presentation.login.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.Shapes
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun InputPassword(
    value: String,
    onValueChanged: (String) -> Unit,
    isVisible: Boolean = false,
    onTrailingIconClick: () -> Unit = {},
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
    val shape = Shapes.medium
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
        supportingText = {
            if (errorMessage.isNotEmpty()) {
                Text(
                    color = MaterialTheme.colorScheme.error,
                    text = errorMessage
                )
            }
        },
        label = {
            Text(
                text = "Password"
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        shape = shape,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { onTrailingIconClick() },
                painter = painterResource(
                    id = if (isVisible)
                        R.drawable.ic_hide
                    else
                        R.drawable.ic_show
                ),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = ""
            )
        }
    )
}

@Preview
@Composable
fun CustomTextFieldPreview() {
    TaskyAppTheme {
        var isVisible by remember {
            mutableStateOf(false)
        }
        Column {
            InputPassword(
                value = "Password",
                onValueChanged = {},
                isVisible = isVisible,
                onTrailingIconClick = { isVisible = !isVisible }
            )
            InputPassword(
                value = "1234aBc.",
                onValueChanged = {},
                isVisible = true

            )
            InputPassword(
                value = "Password",
                onValueChanged = {},
                errorMessage = "Error",
                isVisible = isVisible,
                onTrailingIconClick = { isVisible = !isVisible }
            )
        }
    }
}
