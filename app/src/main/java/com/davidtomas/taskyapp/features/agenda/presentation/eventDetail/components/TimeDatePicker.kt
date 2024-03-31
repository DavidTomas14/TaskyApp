package com.davidtomas.taskyapp.features.agenda.presentation.eventDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun TimeDatePicker(
    label: String,
    hour: String,
    date: String,
    onChangeHourIconClick: () -> Unit,
    onChangeDateIconClick: () -> Unit,
    isEdit: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = label,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                text = hour,
                style = MaterialTheme.typography.titleSmall
            )
            if (isEdit)
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_next),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onChangeHourIconClick() },
                    contentDescription = "More Actions",
                )
            Text(
                modifier = Modifier
                    .weight(if (isEdit) 2f else 4f),
                text = date,
                style = MaterialTheme.typography.titleSmall,
                textAlign = if (isEdit) TextAlign.Left else TextAlign.Center
            )
            if (isEdit)
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_next),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onChangeDateIconClick() },
                    contentDescription = "More Actions",
                )
        }
        Spacer(modifier = Modifier.height(28.dp))
        HorizontalDivider()
    }
}

@Preview
@Composable
fun TimeDatePickerPreview() {
    TaskyAppTheme {
        Column {
            TimeDatePicker(
                label = "From",
                hour = "8:00",
                date = "Jun 21 2022",
                onChangeDateIconClick = {},
                onChangeHourIconClick = {},
                isEdit = false
            )
            TimeDatePicker(
                label = "From",
                hour = "8:00",
                date = "Jun 21 2022",
                onChangeDateIconClick = {},
                onChangeHourIconClick = {},
                isEdit = true
            )

            TimeDatePicker(
                label = "To",
                hour = "8:00",
                date = "Jun 21 2022",
                onChangeDateIconClick = {},
                onChangeHourIconClick = {},
                isEdit = false
            )

            TimeDatePicker(
                label = "To",
                hour = "8:00",
                date = "Jun 21 2022",
                onChangeDateIconClick = {},
                onChangeHourIconClick = {},
                isEdit = true
            )
        }
    }
}