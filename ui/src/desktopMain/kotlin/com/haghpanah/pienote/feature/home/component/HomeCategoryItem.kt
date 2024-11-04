package com.haghpanah.pienote.feature.home.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
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
            PienoteTheme.colors.surfaceBright
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
        image?.let {
            AsyncImage(
                modifier = Modifier
                    .blur(
                        radiusX = 248.dp,
                        radiusY = 248.dp,
                        edgeTreatment = BlurredEdgeTreatment(PienoteTheme.shapes.medium)
                    )
                    .fillMaxWidth(),
                model = it,
                contentDescription = name,
                contentScale = ContentScale.Crop
            )
        }

        if (noteCount != 0) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(PienoteTheme.shapes.rounded)
                    .background(
                        shape = PienoteTheme.shapes.rounded,
                        color = PienoteTheme.colors.background.copy(alpha = 0.4f)
                    )
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                text = "$noteCount ${if (noteCount == 1) "Note" else "Notes"}",
                style = PienoteTheme.typography.titleSmall,
                color = PienoteTheme.colors.onBackground
            )
        }

        Text(
            modifier = Modifier
                .padding(8.dp)
                .then(
                    if (image.isNullOrEmpty()) {
                        Modifier.padding(vertical = 8.dp)
                    } else {
                        Modifier.background(PienoteTheme.colors.background)
                    }
                )
                .align(Alignment.BottomStart)
                .padding(horizontal = 8.dp),
            text = name,
            style = PienoteTheme.typography.titleLarge,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onBackground
        )
    }
}