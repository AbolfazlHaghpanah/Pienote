package com.haghpanh.pienote.commonui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun PienoteChip(
    modifier: Modifier = Modifier,
    backgroundColor: Color = PienoteTheme.colors.background,
    shape: Shape = PienoteTheme.shapes.rounded,
    border: BorderStroke? = BorderStroke(1.dp, PienoteTheme.colors.onBackground.copy(alpha = 0.5f)),
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(PienoteTheme.shapes.rounded)
            .clickable(onClick = onClick),
        color = backgroundColor,
        shape = shape,
        border = border,
        content = content
    )
}
