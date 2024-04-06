package com.haghpanh.pienote.home.ui.component

import android.content.Context
import android.os.Vibrator
import android.view.MotionEvent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.baseui.theme.PienoteTheme

@Composable
fun HomeNoteItem(
    title: String,
    note: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) {
    val context = LocalContext.current

    var draggableDelta by remember { mutableFloatStateOf(0f) }
    val animatedItemColor by animateColorAsState(
        targetValue = if (draggableDelta > 0f) PienoteTheme.colors.error else PienoteTheme.colors.surface,
        label = "change color on delete"
    )
    val draggableState = rememberDraggableState { delta ->
        draggableDelta += delta
    }

    LaunchedEffect(draggableDelta) {
        if (draggableDelta > 240f) {
            (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.apply {
                vibrate(100)
            }
        }
    }

    Column(
        modifier = modifier
            .background(
                color = animatedItemColor,
                shape = PienoteTheme.shapes.veryLarge
            )
            .fillMaxWidth()
            .aspectRatio(2.4f)
            .draggable(
                state = draggableState,
                orientation = Orientation.Horizontal
            )
    ) {
        AnimatedContent(
            targetState = draggableDelta > 0f,
            label = "show delete Icon or item Text",
            transitionSpec = { fadeIn().togetherWith(fadeOut()) }
        ) {
            if (it) {
                Icon(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxHeight()
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