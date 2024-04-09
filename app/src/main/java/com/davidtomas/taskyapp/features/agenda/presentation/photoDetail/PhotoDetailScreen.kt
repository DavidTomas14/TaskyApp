package com.davidtomas.taskyapp.features.agenda.presentation.photoDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun PhotoDetailScreen(
    state: PhotoDetailState,
    onAction: (PhotoDetailAction) -> Unit
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
                        .size(24.dp)
                        .clickable { onAction(PhotoDetailAction.OnCloseIconClicked) },
                    painter = painterResource(id = R.drawable.ic_cancel),
                    contentDescription = null,
                )
            },
            headerText = "Photo",
            textColor = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.titleMedium,
            trailingComposable = {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onAction(PhotoDetailAction.OnDeleteIconClicked) },
                    painter = painterResource(id = R.drawable.ic_delete_action),
                    contentDescription = null,
                )
            }
        )
        AsyncImage(
            model = state.photoUrl,
            contentDescription = "image description",
            modifier = Modifier
                .alpha(0.8f)
                .padding(10.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(5.dp))
                .border(
                    border = BorderStroke(2.dp, Color.Gray)
                ),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Preview
@Composable
fun PhotoDetailScreenPreview() {
    TaskyAppTheme {
        PhotoDetailScreen(
            state = PhotoDetailState(),
            onAction = {}
        )
    }
}
