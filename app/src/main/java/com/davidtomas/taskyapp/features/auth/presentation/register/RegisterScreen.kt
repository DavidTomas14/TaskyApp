package com.davidtomas.taskyapp.features.auth.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.R
import com.davidtomas.taskyapp.core.presentation.components.Header
import com.davidtomas.taskyapp.core.presentation.components.IconD
import com.davidtomas.taskyapp.coreUi.LocalSpacing
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme
import com.davidtomas.taskyapp.features.auth.presentation.components.BasicInput
import com.davidtomas.taskyapp.features.auth.presentation.components.InputPassword

@Composable
fun RegisterScreen() {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.onPrimaryContainer)
        ) {

            RegisterHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer),
            )
            Form(
                modifier = Modifier
                    .weight(3f)
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            topEnd = 20.dp
                        )
                    )
                    .fillMaxWidth()
                    .background(Color.White)
            )
        }
    }
}

@Composable
fun RegisterHeader(
    modifier: Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        Header(
            leadingIcon = {
                IconD(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                )
            }
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            text = stringResource(id = R.string.title_register)
        )
    }
}

@Composable
fun Form(
    modifier: Modifier
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
    ) {
        BasicInput(
            label = stringResource(id = R.string.label_name),
            value = "Username",
            onValueChanged = {}
        )
        BasicInput(
            label = stringResource(id = R.string.label_email),
            value = "dtalonsocalderon@gmail.com",
            onValueChanged = {}
        )
        InputPassword(
            value = "Password",
            onValueChanged = {}
        )
        Button(
            modifier = Modifier
                .padding(
                    start = spacing.spaceLarge,
                    top = spacing.spaceLarge,
                    end = spacing.spaceLarge
                )
                .fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = stringResource(id = R.string.btn_text_register))
        }
    }
}

@Preview(
    showBackground = true,
    device = "spec:width=1080px,height=2400px",
    name = "Redmin Note 10 Pro"
)
@Composable
fun RegisterPreview() {
    TaskyAppTheme {
        RegisterScreen()
    }
}
