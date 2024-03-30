package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun CardItem2(
    title: String,
    description: String,
    date: String,
    isDone: Boolean,
    isDropDownMenuShown: Boolean,
    onDismissDropDownMenu: () -> Unit,
    onCardClick: () -> Unit,
    onBulletClick: () -> Unit,
    onOptionsClick: () -> Unit,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    onPrimaryColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier,
    onOpenOptionClick: () -> Unit,
    onEditOptionClick: () -> Unit,
    onDeleteOptionClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = primaryColor
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isDone)
                            R.drawable.ic_circle_checked
                        else R.drawable.ic_circle_no_check
                    ),
                    modifier = Modifier
                        .clickable { onBulletClick() }
                        .padding(end = 8.dp),
                    contentDescription = "More Actions",
                    tint = onPrimaryColor
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            textDecoration =
                            if (isDone)
                                TextDecoration.LineThrough
                            else TextDecoration.None
                        ),
                        color = onPrimaryColor
                    )
                    Text(
                        text = description,
                        color = onPrimaryColor.copy(0.8f),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Box() {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_actions),
                        modifier = Modifier
                            .clickable { onOptionsClick() },
                        contentDescription = "More Actions",
                        tint = onPrimaryColor
                    )
                    DropDownAgendaItemOptions(
                        isShown = isDropDownMenuShown,
                        onDismissRequest = onDismissDropDownMenu,
                        dropDownItems = listOf(
                            DropDownItems(
                                text = "Open",
                                leadingIcon = R.drawable.ic_open_action,
                                onClick = onOpenOptionClick
                            ),
                            DropDownItems(
                                text = "Edit",
                                leadingIcon = R.drawable.ic_edit_action,
                                onClick = onEditOptionClick
                            ),
                            DropDownItems(
                                text = "Delete",
                                leadingIcon = R.drawable.ic_delete_action,
                                onClick = onDeleteOptionClick
                            )
                        )
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 40.dp),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
                text = date,
                color = onPrimaryColor.copy(0.8f)
            )
        }
    }
}

@Preview
@Composable
fun CardItem2Preview() {
    TaskyAppTheme {
        Column {
            CardItem2(
                title = "Project X",
                description = "Just Work",
                date = "Mar 5, 10:00",
                isDone = false,
                isDropDownMenuShown = true,
                onDismissDropDownMenu = {},
                onCardClick = { /*TODO*/ },
                onBulletClick = { /*TODO*/ },
                onOptionsClick = { /*TODO*/ },
                onOpenOptionClick = {},
                onEditOptionClick = {}
            ) {}
            Spacer(modifier = Modifier.height(300.dp))
            CardItem2(
                title = "Project X",
                description = "Just Work",
                date = "Mar 5, 10:00",
                isDone = true,
                isDropDownMenuShown = false,
                onDismissDropDownMenu = {},
                onCardClick = { /*TODO*/ },
                onBulletClick = { /*TODO*/ },
                onOptionsClick = { /*TODO*/ },
                onOpenOptionClick = {},
                onEditOptionClick = {}
            ) {}
            Spacer(modifier = Modifier.height(300.dp))
        }
    }
}