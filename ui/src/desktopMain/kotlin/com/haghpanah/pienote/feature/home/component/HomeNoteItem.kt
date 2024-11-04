package com.haghpanah.pienote.feature.home.component

import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.core.theme.PienoteTheme
import com.haghpanah.pienote.core.utlis.toComposeColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    color: String?,
    isSelected: Boolean,
    isShowing: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit = {}
) {
    val density = LocalDensity.current
    val cornerSize = PienoteTheme
        .shapes
        .medium
        .topStart
        .toPx(shapeSize = Size.Zero, density)
    val animatedIsSelectedSize by animateSizeAsState(
        targetValue = if (isSelected) {
            Size(cornerSize, cornerSize)
        } else {
            Size.Zero
        },
        label = "is selected label size"
    )

    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.medium)
            .background(
                if (isShowing) {
                    PienoteTheme.colors.surfaceDim
                } else {
                    PienoteTheme.colors.surfaceContainerLowest
                }
            )
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
    ) {
        val selectedColor = PienoteTheme.colors.tertiaryContainer

        Column(
            Modifier
                .fillMaxSize()
                .drawWithContent {
                    color?.let { color ->
                        drawRoundRect(
                            color = color.toComposeColor(),
                            topLeft = Offset(x = size.width - cornerSize, y = 0f)
                        )
                    }
                    drawContent()
                }
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
                    .fillMaxWidth()
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                text = title,
                style = PienoteTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = PienoteTheme.colors.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                text = note,
                style = PienoteTheme.typography.bodySmall,
                overflow = TextOverflow.Ellipsis,
                color = PienoteTheme.colors.onSurface
            )
        }
    }
}

@Preview()
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
                color = "#443233",
                isSelected = true,
                onClick = { /*TODO*/ },
                onLongClick = {},
                isShowing = false
            )
        }
    }
}
