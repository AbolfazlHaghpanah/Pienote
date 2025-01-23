package com.haghpanah.pienote.feature.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.core.theme.PienoteTheme

@Composable
fun HomeCategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    isShowing: Boolean,
    image: String? = null,
    noteCount: Int,
    onClick: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (isShowing) {
            PienoteTheme.colors.secondaryContainer
        } else {
            PienoteTheme.colors.surfaceContainerLowest
        }
    )

    Box(
        modifier = modifier
            .clip(PienoteTheme.shapes.small)
            .background(
                color = backgroundColor,
                shape = PienoteTheme.shapes.medium
            )
            .clickable(onClick = onClick)
    ) {
        if (noteCount != 0) {
            AnimatedVisibility(
                visible = !isShowing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Badge(
                    containerColor = PienoteTheme.colors.secondaryContainer,
                    contentColor = PienoteTheme.colors.onSecondaryContainer
                ) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "$noteCount ${if (noteCount == 1) "Note" else "Notes"}",
                        style = PienoteTheme.typography.titleSmall,
                        color = PienoteTheme.colors.onBackground
                    )
                }
            }
        }

        Text(
            modifier = Modifier
                .padding(8.dp)
                .padding(vertical = 8.dp)
                .align(Alignment.BottomStart)
                .padding(horizontal = 8.dp),
            text = name,
            style = PienoteTheme.typography.titleLarge,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onSecondaryContainer
        )
    }
}