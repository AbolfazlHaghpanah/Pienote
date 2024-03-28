package com.haghpanh.pienote.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.baseui.theme.PienoteTheme


@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .background(
                color = PienoteTheme.colors.surface,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
    ) {
        Text(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            text = title,
            style = PienoteTheme.typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSurface
        )

        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp),
            text = note,
            style = PienoteTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSurface
        )
    }
}