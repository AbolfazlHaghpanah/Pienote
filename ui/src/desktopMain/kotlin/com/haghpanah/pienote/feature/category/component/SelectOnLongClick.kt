package com.haghpanah.pienote.feature.category.component

import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import com.haghpanah.pienote.core.theme.PienoteTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.selectOnLongClick(
    isSelected: Boolean,
    shape: CornerBasedShape = PienoteTheme.shapes.medium,
    onSelectChanged: (Boolean) -> Unit,
    onClick: () -> Unit
): Modifier {
    val selectedColor = PienoteTheme.colors.tertiaryContainer
    val density = LocalDensity.current
    val cornerSize = shape
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

    return this
        .combinedClickable(
            onClick = {
                if (isSelected) {
                    onSelectChanged(false)
                } else {
                    onClick()
                }
            },
            onLongClick = {
                onSelectChanged(!isSelected)
            }
        )
        .drawWithContent {
            drawRoundRect(
                color = selectedColor,
                size = animatedIsSelectedSize,
                cornerRadius = CornerRadius(8f)
            )
            drawContent()
        }
        .padding(start = with(density) { animatedIsSelectedSize.width.toDp() } / 2)

}