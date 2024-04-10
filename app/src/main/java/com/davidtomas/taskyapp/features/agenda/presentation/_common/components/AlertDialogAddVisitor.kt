package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.auth.presentation._common.components.BasicInput

@Composable
fun AlertDialogAddVisitor(
    isEmailChecked: Boolean,
    emailErrMsg: String?,
    onEmailInputFocusChanged: (Boolean) -> Unit,
    onEmailChanged: (String) -> Unit,
    onAddButtonClicked: () -> Unit,
    onCloseIconClicked: () -> Unit
) {
    var visitorEmail by remember {
        mutableStateOf(String.EMPTY_STRING)
    }
    val spacing = LocalSpacing.current
    AlertDialog(
        onDismissRequest = { },
        {
            Column(
                modifier = Modifier
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        modifier = Modifier.clickable {
                            onCloseIconClicked()
                        },
                        painter = painterResource(id = R.drawable.ic_cancel),
                        contentDescription = ""
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center,
                    text = "Add Visitor"
                )
                BasicInput(
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    inputText = visitorEmail,
                    errorMessage = emailErrMsg,
                    onInputTextChanged = {
                        visitorEmail = it
                        onEmailChanged(it)
                    },
                    onInputFocusChanged = {
                        onEmailInputFocusChanged(it)
                    },
                    label = "Email"
                )
                Button(
                    modifier = Modifier
                        .padding(
                            top = spacing.spaceSmall,
                        )
                        .fillMaxWidth(),
                    enabled = isEmailChecked && visitorEmail.isNotEmpty(),
                    onClick = {
                        onAddButtonClicked()
                    },
                ) {
                    Text(text = stringResource(id = R.string.btn_add_visitor))
                }
            }
        }
    )
}

@Preview
@Composable
fun AlertDialogAddVisitorPreview() {
    TaskyAppTheme {
        AlertDialogAddVisitor(
            isEmailChecked = false,
            emailErrMsg = "Eeeerrroooor",
            onEmailInputFocusChanged = {},
            onEmailChanged = {},
            onAddButtonClicked = {},
            onCloseIconClicked = {}
        )
    }
}