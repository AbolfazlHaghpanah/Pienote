package com.haghpanah.pienote.feature.category.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.core.texteditor.utils.renderMarkdownToAnnotatedString
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.core.utlis.toComposeColor

@Composable
internal fun CategoryNoteItem(
    title: String,
    markdown: String,
    color: String?,
    onClick: () -> Unit,
    isSelected: Boolean,
    onSelectChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.medium)
            .widthIn(min = 200.dp, max = 310.dp)
            .height(240.dp)
            .background(
                color = color
                    .takeIf { !it.isNullOrBlank() }
                    ?.toComposeColor()
                    ?: PienoteTheme.colors.surface,
                shape = PienoteTheme.shapes.medium
            )
            .selectOnLongClick(
                onClick = onClick,
                onSelectChanged = onSelectChanged,
                isSelected = isSelected
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = PienoteTheme.typography.titleLarge,
            color = if (color.isNullOrBlank()) {
                PienoteTheme.colors.onSurface
            } else {
                PienoteTheme.colors.background
            }
        )

        Text(
            text = renderMarkdownToAnnotatedString(markdown),
            color = if (color.isNullOrBlank()) {
                PienoteTheme.colors.onSurface
            } else {
                PienoteTheme.colors.background
            }
        )
    }
}