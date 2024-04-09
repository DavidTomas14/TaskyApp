package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun Photos(
    photos: List<String>,
    onAddedPhoto: (String) -> Unit,
    onPhotoClicked: (String) -> Unit,
    isEditable: Boolean
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

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(17.dp))
        Text(
            text = "Photos",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        if (photos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .then(
                            if (isEditable) Modifier
                                .clickable {
                                    singlePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                } else Modifier,
                        ),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_icon),
                        contentDescription = ""
                    )
                    Text(
                        text = "Add photos"
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                val chunkedPhotosList = photos.chunked(5)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (listOf5photos in chunkedPhotosList) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (photo in listOf5photos) {
                                AsyncImage(
                                    model = photo,
                                    contentDescription = "image description",
                                    modifier = Modifier
                                        .alpha(0.8f)
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .border(
                                            border = BorderStroke(2.dp, Color.Gray)
                                        )
                                        .clickable {
                                            onPhotoClicked(photo)
                                        },
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                            if (listOf5photos.size < 5 && isEditable) {
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
                                                PickVisualMediaRequest(
                                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                                )
                                            )
                                        },
                                    contentScale = ContentScale.Fit
                                )
                                repeat(4 - listOf5photos.size) {
                                    Text(text = "", modifier = Modifier.size(60.dp))
                                }
                            } else {
                                repeat(5 - listOf5photos.size) {
                                    Text(text = "", modifier = Modifier.size(60.dp))
                                }
                            }
                        }
                        if (photos.size % 5 == 0 && isEditable) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier.fillMaxWidth()
                            ) {
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
                                                PickVisualMediaRequest(
                                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                                )
                                            )
                                        },
                                    contentScale = ContentScale.Fit
                                )
                                repeat(4) {
                                    Text(text = "", modifier = Modifier.size(60.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(17.dp))
    }
}

class ParametersProvider(
    val photos: List<String>
)

class PhotoTypeProvider : PreviewParameterProvider<ParametersProvider> {
    override val values: Sequence<ParametersProvider>
        get() = sequenceOf(
            ParametersProvider(
                listOf(
                    "", "", "", "", ""
                ),
            ),
            ParametersProvider(
                listOf(),
            )
        )
}

@Preview
@Composable
fun PhotosComposablePreview(
    @PreviewParameter(PhotoTypeProvider::class) previewParameters: ParametersProvider,
) {
    TaskyAppTheme {
        Photos(
            photos = previewParameters.photos,
            onAddedPhoto = {},
            onPhotoClicked = {},
            isEditable = true
        )
    }
}