package com.haghpanh.pienote.home.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.baseui.theme.PienoteTheme
import com.haghpanh.pienote.baseui.utils.SwipeHandlerState.Swipe
import com.haghpanh.pienote.baseui.utils.rememberSwipeHandlerState
import com.haghpanh.pienote.baseui.utils.swipeHandler

@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) {
    val swipeHandlerState = rememberSwipeHandlerState(threshold = 240f)
    val rightSwipe: Swipe by remember {
        derivedStateOf { swipeHandlerState.rightSwipe }
    }
    val animatedItemColor by animateColorAsState(
        targetValue = if (rightSwipe.isSwiped && rightSwipe.isOffsetAchieveThreshold) {
            PienoteTheme.colors.error
        } else if (rightSwipe.isSwiped) {
            PienoteTheme.colors.secondary
        } else {
            PienoteTheme.colors.surface
        },
        label = "change color on delete"
    )
    val animatedRotation by animateFloatAsState(
        targetValue = if (rightSwipe.isOffsetAchieveThreshold) 0f else 360f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy),
        label = "delete icon rotation Animation"
    )

    Column(
        modifier = modifier
            .background(
                color = animatedItemColor,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
            .swipeHandler(
                state = swipeHandlerState,
                onSwipeRight = onDelete,
                shouldVibrateOnAchieveThreshold = true
            )

    ) {
        AnimatedContent(
            targetState = rightSwipe.isSwiped,
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

        }
    }
}