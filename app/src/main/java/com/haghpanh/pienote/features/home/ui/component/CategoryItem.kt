package com.haghpanh.pienote.features.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun HomeCategoryItem(
    modifier: Modifier = Modifier,
    name: String,
    image: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(PienoteTheme.shapes.small)
            .background(
                color = PienoteTheme.colors.surfaceContainerLowest,
                shape = PienoteTheme.shapes.medium
            )
            .clickable(onClick = onClick)
    ) {
        image?.let {
            AsyncImage(
                modifier = Modifier
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment(PienoteTheme.shapes.medium)
                    )
                    .fillMaxWidth(),
                model = it,
                contentDescription = name,
                contentScale = ContentScale.Crop
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
            style = PienoteTheme.typography.headlineSmall,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = PienoteTheme.colors.onBackground
        )
    }
}
