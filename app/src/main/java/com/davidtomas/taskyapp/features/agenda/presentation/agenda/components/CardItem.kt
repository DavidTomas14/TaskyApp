package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun CardItem(
    title: String,
    description: String,
    date: String,
    isChecked: Boolean,
    onCardClick: () -> Unit,
    onBulletClick: () -> Unit,
    onOptionsClick: () -> Unit,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    onPrimaryColor: Color = MaterialTheme.colorScheme.onPrimary,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = primaryColor
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            val (leadingIcon, titleTxt, descriptionTxt, trailingIcon, dateTxt) = createRefs()
            Icon(
                painter = painterResource(
                    id = if (isChecked)
                        R.drawable.ic_circle_checked
                    else R.drawable.ic_circle_no_check
                ),
                modifier = Modifier
                    .clickable { onBulletClick() }
                    .constrainAs(leadingIcon) {
                        start.linkTo(parent.start)
                    }
                    .padding(end = 8.dp),
                contentDescription = "More Actions",
                tint = onPrimaryColor
            )
            Text(
                text = title,
                modifier = Modifier
                    .constrainAs(titleTxt) {
                        top.linkTo(leadingIcon.top)
                        bottom.linkTo(leadingIcon.bottom)
                        start.linkTo(leadingIcon.end)
                    },
                style = MaterialTheme.typography.titleMedium.copy(
                    textDecoration =
                    if (isChecked)
                        TextDecoration.LineThrough
                    else TextDecoration.None
                ),
                color = onPrimaryColor
            )
            Text(
                text = description,
                modifier = Modifier.constrainAs(descriptionTxt) {
                    top.linkTo(titleTxt.bottom, margin = 16.dp)
                    start.linkTo(leadingIcon.end)
                },
                color = onPrimaryColor.copy(0.8f),
                style = MaterialTheme.typography.titleSmall
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_more_actions),
                modifier = Modifier
                    .constrainAs(trailingIcon) {
                        end.linkTo(parent.end)
                    }
                    .clickable { onOptionsClick() },
                contentDescription = "More Actions",
                tint = onPrimaryColor
            )
            Text(
                text = date,
                modifier = Modifier.constrainAs(dateTxt) {
                    top.linkTo(descriptionTxt.bottom, margin = 40.dp)
                    end.linkTo(trailingIcon.end)
                },
                style = MaterialTheme.typography.bodyMedium,
                color = onPrimaryColor.copy(0.8f)
            )
        }
    }
}

object CardColorsDefaults {
    class CardColors(
        val cardColor: Color,
        val onPrimaryColor: Color,
        val secondaryColor: Color
    )

    @Composable
    fun buttonColors(
        cardColor: Color = MaterialTheme.colorScheme.primary,
        onPrimaryColor: Color = MaterialTheme.colorScheme.onPrimary,
        secondaryColor: Color = MaterialTheme.colorScheme.onTertiary,
    ) = CardColors(
        cardColor = cardColor,
        onPrimaryColor = onPrimaryColor,
        secondaryColor = secondaryColor
    )
}

@Preview
@Composable
fun CardItemPreview() {
    TaskyAppTheme {
        Column {
            CardItem(
                title = "Project X",
                description = "Just Work",
                date = "Mar 5, 10:00",
                isChecked = false,
                onCardClick = { /*TODO*/ },
                onBulletClick = { /*TODO*/ },
                onOptionsClick = { /*TODO*/ }
            )
            Spacer(modifier = Modifier.height(20.dp))
            CardItem(
                title = "Project X",
                description = "Just Work",
                date = "Mar 5, 10:00",
                isChecked = true,
                onCardClick = { /*TODO*/ },
                onBulletClick = { /*TODO*/ },
                onOptionsClick = { /*TODO*/ }
            )
        }
    }
}