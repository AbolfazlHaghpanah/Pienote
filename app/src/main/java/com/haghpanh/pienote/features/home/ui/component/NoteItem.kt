package com.haghpanh.pienote.features.home.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.SwipeState
import com.haghpanh.pienote.commonui.utils.rememberSwipeState
import com.haghpanh.pienote.commonui.utils.swipeHandler

@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val swipeState = rememberSwipeState(
        threshold = 240f,
        swipeType = SwipeState.LeftToRight()
    )

    val animatedItemColor by animateColorAsState(
        targetValue = if (
            swipeState.directionalSwipe.isSwiped &&
            swipeState.directionalSwipe.isOffsetAchieveThreshold
        ) {
            PienoteTheme.colors.error
        } else if (swipeState.directionalSwipe.isSwiped) {
            PienoteTheme.colors.secondary
        } else {
            PienoteTheme.colors.surface
        },
        label = "change color on delete"
    )
    val animatedRotation by animateFloatAsState(
        targetValue = if (swipeState.directionalSwipe.isOffsetAchieveThreshold) 0f else 360f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "delete icon rotation Animation"
    )

    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .clickable(onClick = onClick)
            .background(
                color = animatedItemColor,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
            .swipeHandler(
                state = swipeState,
                onSwipeRight = onDelete,
                shouldVibrateOnAchieveThreshold = true
            )

    ) {
        AnimatedContent(
            targetState = swipeState.directionalSwipe.isSwiped,
            label = "show delete Icon or item Text",
            transitionSpec = { fadeIn().togetherWith(fadeOut()) }
        ) {
            if (it) {
                Icon(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxHeight()
                        .rotate(animatedRotation)
                        .aspectRatio(1f),
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "delete"
                )
            } else {
                Column {
                    Text(
                        modifier = Modifier
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                        text = title,
                        style = PienoteTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = PienoteTheme.colors.onSurface
                    )

                    Text(
                        modifier = Modifier
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        text = note,
                        style = PienoteTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        color = PienoteTheme.colors.onSurface
                    )
                }
            }
        }
    }
}
