package com.davidtomas.taskyapp.features.agenda.presentation.agenda.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidtomas.taskyapp.coreUi.TaskyAppTheme

@Composable
fun Needle(
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(12.dp)
    ) {
        val lineStroke = 4.dp.toPx()
        val circleRadius = 6.dp.toPx()
        drawLine(
            Color.Black,
            start = Offset(1f, size.height / 2),
            end = Offset(size.maxDimension, size.height / 2),
            strokeWidth = lineStroke
        )
        drawCircle(
            center = Offset(6.dp.toPx(), size.height / 2),
            color = Color.Black,
            radius = circleRadius,
        )
    }
}

@Preview
@Composable
fun NeedlePreview() {
    TaskyAppTheme {
        Needle()
    }
}