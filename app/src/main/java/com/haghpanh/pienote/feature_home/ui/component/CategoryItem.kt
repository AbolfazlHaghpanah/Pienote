package com.haghpanh.pienote.feature_home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.common_ui.theme.PienoteTheme

@Composable
fun HomeCategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    priority: Int?,
    onClick: () -> Unit
) {
    val priorityIcon = when (priority) {
        in 1..3 -> PienoteTheme.icon.highPriority
        in 4..6 -> PienoteTheme.icon.mediumDensity
        in 6..9 -> PienoteTheme.icon.lowPriority
        else -> null
    }

    Box(
        modifier = modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .background(
                color = PienoteTheme.colors.surface,
                shape = PienoteTheme.shapes.veryLarge
            )
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .aspectRatio(2.4f),
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 24.dp),
            text = name,
            style = PienoteTheme.typography.h2,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSurface
        )

        priorityIcon?.let {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(24.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .alpha(0.3f),
                painter = painterResource(id = priorityIcon),
                contentDescription = null,
                tint = PienoteTheme.colors.onSurface
            )
        }
    }
}