package com.davidtomas.taskyapp.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.core.domain._util.EMPTY_STRING
import com.davidtomas.taskyapp.coreUi.LocalSpacing

@Composable
fun Header(
    modifier: Modifier = Modifier,
    headerText: String = String.EMPTY_STRING,
    leadingComposable: @Composable (() -> Unit)? = {},
    trailingComposable: @Composable (() -> Unit)? = {},
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = modifier
            .padding(spacing.spaceSmall)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingComposable?.let { it() }
        Text(
            modifier = Modifier
                .weight(1f),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            text = headerText
        )
        trailingComposable?.let { it() }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(
                modifier = Modifier.background(Color.Black),
                headerText = "Header Text",
                leadingComposable = {
                    IconD(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    )
                },
                trailingComposable = {
                    IconD(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                    )
                }
            )
        }
    }
}