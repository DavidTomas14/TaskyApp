package com.davidtomas.taskyapp.features.agenda.presentation.editTitleOrDescription

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun EditTitleOrDescriptionScreen(
    editType: EditType,
    text: String,
    onTextChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackIconClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            leadingComposable = {
                Icon(
                    modifier = Modifier
                        .size(12.dp)
                        .clickable { onBackIconClick() },
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = null,
                )
            },
            headerText = when (editType) {
                EditType.TITLE -> "EDIT TITLE"
                EditType.DESCRIPTION -> "EDIT DESCRIPTION"
            },
            textColor = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.titleMedium,
            trailingComposable = {
                Text(
                    modifier = Modifier
                        .clickable {
                            onSaveClick()
                        },
                    style = MaterialTheme.typography.titleSmall,
                    text = "Save",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
        HorizontalDivider(
            modifier = Modifier
                .padding(
                    horizontal = 17.dp
                )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = text,
            onValueChange = onTextChanged,
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
            )

        )
    }
}

enum class EditType {
    TITLE, DESCRIPTION
}

@Preview
@Composable
fun EditTitleOrDescriptionScreenPreview() {
    var text by remember { mutableStateOf("") }
    TaskyAppTheme {
        EditTitleOrDescriptionScreen(
            text = text,
            onTextChanged = {
                text = it
            },
            editType = EditType.TITLE,
            onSaveClick = {},
            onBackIconClick = {}
        )
    }
}