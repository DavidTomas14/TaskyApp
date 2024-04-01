package com.davidtomas.taskyapp.features.agenda.presentation.agendaDetail.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.agenda.domain.model.PhotoModel

@Composable
fun Photos(
    photos: List<PhotoModel>,
    onAddedPhoto: (String) -> Unit,
) {
    val context = LocalContext.current
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            uri?.toString()?.let { imageUrl ->
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)
                onAddedPhoto(imageUrl)
            }
        }
    )
    Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(
            text = "Photos",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            val chunkedTextList = photos.chunked(5)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (chunk in chunkedTextList) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        for (text in chunk) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_add_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .alpha(0.8f)
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .border(
                                        border = BorderStroke(2.dp, Color.Gray)
                                    ),
                                contentScale = ContentScale.Fit
                            )
                        }
                        if (chunk.size < 5) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_add_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .alpha(0.8f)
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .border(
                                        border = BorderStroke(2.dp, Color.Gray)
                                    )
                                    .clickable {
                                        singlePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                contentScale = ContentScale.Fit
                            )
                            repeat(4 - chunk.size) {
                                Text(text = "", modifier = Modifier.size(60.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PhotosComposablePreview() {
    TaskyAppTheme {
        Photos(
            photos = listOf(
                PhotoModel("", ""),
                PhotoModel("", ""),
                PhotoModel("", ""),
                PhotoModel("", ""),
                PhotoModel("", ""),
                PhotoModel("", ""),
            ),
            onAddedPhoto = {}
        )
    }
}