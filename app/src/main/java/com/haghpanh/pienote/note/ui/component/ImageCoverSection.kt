package com.haghpanh.pienote.note.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.haghpanh.pienote.R
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Composable
fun ImageCoverSection(
    image: String?,
    isEditing: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    AnimatedVisibility(visible = image != null) {
        Box(
            modifier = modifier
                .clip(PienoteTheme.shapes.large)
                .then(
                    if (isEditing) {
                        Modifier.clickable(onClick = onClick)
                    } else {
                        Modifier
                    }
                )
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = image,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            AnimatedVisibility(
                visible = !isEditing,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    PienoteTheme.colors.background
                                )
                            )
                        )
                )
            }
        }
    }

    AnimatedContent(
        targetState = isEditing && image == null,
        label = "is editing and don have image"
    ) { editing ->
        if (editing) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    modifier = modifier.align(Alignment.Center),
                    onClick = onClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = PienoteTheme.colors.onBackground.copy(alpha = 0.3f)
                    )
                ) {
                    Text(text = stringResource(R.string.add_cover_image))
                }
            }
        } else if(image == null) {
            Spacer(modifier = modifier)
        }
    }
}
