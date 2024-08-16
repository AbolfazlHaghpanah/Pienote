package com.haghpanh.pienote.features.home.ui.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.SwipeState
import com.haghpanh.pienote.commonui.utils.rememberSwipeState
import com.haghpanh.pienote.commonui.utils.swipeHandler

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val animatedIsSelectedSize by animateSizeAsState(
        targetValue = if (isSelected) {
            val cornerSize = PienoteTheme
                .shapes
                .veryLarge
                .topStart
                .toPx(shapeSize = Size.Zero, density)

            Size(cornerSize, cornerSize)
        } else {
            Size.Zero
        },
        label = "is selected label size"
    )

    val swipeState = rememberSwipeState(
        threshold = 240f,
        swipeType = SwipeState.LeftToRight()
    )

    val animatedItemColor by animateColorAsState(
        targetValue = if (
            swipeState.directionalSwipe.isSwiped &&
            swipeState.directionalSwipe.isOffsetAchieveThreshold
        ) {
            PienoteTheme.colors.errorContainer
        } else if (swipeState.directionalSwipe.isSwiped) {
            PienoteTheme.colors.secondaryContainer
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
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
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
                    contentDescription = "delete",
                    tint = PienoteTheme.colors.onSecondaryContainer
                )
            } else {
                val selectedColor = PienoteTheme.colors.tertiaryContainer

                Column(
                    Modifier
                        .drawWithContent {
                            drawRoundRect(
                                color = selectedColor,
                                size = animatedIsSelectedSize,
                                cornerRadius = CornerRadius(8f)
                            )
                            drawContent()
                        }
                        .padding(start = with(density) { animatedIsSelectedSize.width.toDp() } / 2)
                ) {
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

@Preview(
    wallpaper = Wallpapers.BLUE_DOMINATED_EXAMPLE,
    backgroundColor = 0xFF3F51B5,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeNoteItemPreview() {
    PienoteTheme {
        Box(
            modifier = Modifier
                .background(PienoteTheme.colors.background)
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            HomeNoteItem(
                title = "Morteza",
                note = "Give Me Some more",
                isSelected = true,
                onDelete = { /*TODO*/ },
                onClick = { /*TODO*/ },
                onLongClick = {}
            )
        }
    }
}
