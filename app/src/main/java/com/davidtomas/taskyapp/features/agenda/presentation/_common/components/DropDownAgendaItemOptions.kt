package com.davidtomas.taskyapp.features.agenda.presentation._common.components

import android.widget.Toast
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun DropDownAgendaItemOptions() {
    val contextForToast = LocalContext.current
    var expanded by remember { mutableStateOf(true) }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { /*TODO*/ }
    ) {
        DropdownMenuItem(
            text = {
                Text("Open")
            },
            onClick = {
                Toast.makeText(contextForToast, "¡Open😎!", Toast.LENGTH_SHORT).show()
                expanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_action),
                    contentDescription = "More Actions"
                )
            }
        )

        DropdownMenuItem(
            text = {
                Text("Edit")
            },
            onClick = {
                Toast.makeText(contextForToast, "Edit🙏", Toast.LENGTH_SHORT).show()
                expanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit_action),
                    contentDescription = "More Actions"
                )
            }
        )

        DropdownMenuItem(
            text = {
                Text("Delete")
            },
            onClick = {
                Toast.makeText(contextForToast, "Delete", Toast.LENGTH_SHORT)
                    .show()
                expanded = false
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete_action),
                    contentDescription = "More Actions"
                )
            }
        )
    }
}

@Preview
@Composable
fun DropDownAgendaItemOptionsPreview() {
    TaskyAppTheme {
        DropDownAgendaItemOptions()
    }
}