package com.davidtomas.taskyapp.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.LocalSpacing

@Composable
fun Header(
    modifier: Modifier = Modifier,
    headerText: String = String.EMPTY_STRING,
    leadingIcon: @Composable (() -> Unit)? = {},
    trailingIcon: @Composable (() -> Unit)? = {},
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .padding(spacing.spaceSmall)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingIcon?.let { it() }
        Text(
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            text = headerText
        )
        trailingIcon?.let { it() }
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    headerText: String = String.EMPTY_STRING,
    leadingIcon: ImageVector? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
) {
    Header(
        modifier = modifier,
        headerText = headerText,
        leadingIcon = {
            leadingIcon?.run {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onLeadingIconClick?.let {
                                it()
                            }
                        },
                    imageVector = this,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = ""
                )
            }
        },
        trailingIcon = {
            trailingIcon?.run {
                IconD(
                    modifier = Modifier
                        .clickable {
                            onTrailingIconClick?.let {
                                it()
                            }
                        },
                    imageVector = this,
                    contentDescription = null
                )
            }
        }
    )
}

@Preview
@Composable
fun HeaderPreview() {
    MaterialTheme {
        Column {
            Header(
                headerText = "Header Text",
                leadingIcon = {
                    IconD(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    )
                },
                trailingIcon = {
                    IconD(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                    )
                }
            )

            Header(
                headerText = "Header Text",
                leadingIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onLeadingIconClick = {},
                trailingIcon = Icons.AutoMirrored.Filled.Send,
                onTrailingIconClick = {}
            )
        }
    }
}
