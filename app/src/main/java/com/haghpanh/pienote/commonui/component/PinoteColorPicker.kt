package com.haghpanh.pienote.commonui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.toComposeColor

@Composable
fun PienoteColorPicker(
    color: String?,
    onColorSelection: (String) -> Unit,
    modifier: Modifier = Modifier,
    colors: List<String> = colorPickerDefaultColors
) {
    LazyHorizontalGrid(
        modifier = modifier
            .height(290.dp)
            .clip(PienoteTheme.shapes.veryLarge)
            .background(PienoteTheme.colors.surfaceDim)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        rows = GridCells.Fixed(4),
        contentPadding = PaddingValues(24.dp),
    ) {
        items(colors) {
            val animatedBorderWidth by animateDpAsState(
                targetValue = if (color == it) {
                    8.dp
                } else {
                    24.dp
                },
                label = "change border width"
            )

            AnimatedVisibility(
                modifier = Modifier,
                visible = color == it
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .aspectRatio(1f)
                        .padding(10.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "selected color",
                    tint = it.toComposeColor()
                )
            }

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .aspectRatio(1f)
                    .clip(PienoteTheme.shapes.rounded)
                    .clickable { onColorSelection(it) }
                    .border(
                        width = animatedBorderWidth,
                        color = it
                            .toComposeColor()
                            .copy(alpha = 0.8f),
                        shape = PienoteTheme.shapes.rounded
                    )
            )
        }
    }
}

@Preview
@Composable
private fun PienoteColorPickerPreview() {
    PienoteTheme {
        Box(modifier = Modifier.fillMaxWidth()) {
            PienoteColorPicker(
                color = "#FFD700",
                onColorSelection = {}
            )
        }
    }
}

private val colorPickerDefaultColors = listOf(
    "#F0F8FF", // Alice Blue
    "#D3D3D3", // Light Gray
    "#E0FFFF", // Light Cyan
    "#FFFAF0", // Floral White
    "#FFFF99", // Light Yellow
    "#F0E68C", // Khaki
    "#F4A460", // Sandy Brown
    "#FFD700", // Gold
    "#FFCC99", // Peach
    "#FFB6C1", // Light Pink
    "#FF6347", // Tomato
    "#FF7F50", // Coral
    "#FF4500", // Orange Red
    "#FF8C00", // Dark Orange
    "#FF0000", // Red
    "#FF1493", // Deep Pink
    "#FF69B4", // Hot Pink
    "#C71585", // Medium Violet Red
    "#D2691E", // Chocolate
    "#8B4513", // Saddle Brown
    "#8A2BE2", // Blue Violet
    "#6A5ACD", // Slate Blue
    "#7B68EE", // Medium Slate Blue
    "#4682B4", // Steel Blue
    "#87CEEB", // Sky Blue
    "#00CED1", // Dark Turquoise
    "#20B2AA", // Light Sea Green
    "#00FF00", // Lime
    "#90EE90", // Light Green
    "#B0E57C", // Light Green
    "#ADFF2F", // Green Yellow
    "#BDB76B", // Dark Khaki
    "#C0C0C0", // Silver
    "#FFA07A", // Light Salmon
    "#F08080" // Light Coral
)
