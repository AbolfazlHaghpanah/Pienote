package com.haghpanah.pienote.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlin.math.abs

private const val SLIDE_OUT_FROM_BOTTOM = 0
private const val SLIDE_OUT_FROM_RIGHT = 1
private const val SLIDE_OUT_FROM_LEFT = 2

/**
 * Composable function that hosts the snackbar.
 *
 * @param manager The SnackbarManager responsible for managing snackbar messages.
 */
@Composable
fun PienoteSnackbarHost(manager: SnackbarManager) {
    var currentSnackbarData: SnackbarData? by remember { mutableStateOf(null) }
    var shouldShowSnackbar by remember { mutableStateOf(false) }
    var slideOutAnimationId by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        manager.currentMessage.collectLatest { snackbarData ->
            currentSnackbarData = snackbarData
            shouldShowSnackbar = snackbarData != null

            if (snackbarData != null) {
                val duration = when (snackbarData.duration) {
                    SnackbarDuration.Short -> 3000L
                    SnackbarDuration.Long -> 5000L
                    SnackbarDuration.BasedOnMessage -> snackbarData.calculateDurationBasedOnText()
                    SnackbarDuration.Infinite -> null
                }

                if (duration != null) {
                    delay(duration)
                    shouldShowSnackbar = false
                }
            }
        }
    }

    AnimatedVisibility(
        visible = shouldShowSnackbar,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = when (slideOutAnimationId) {
            SLIDE_OUT_FROM_RIGHT -> slideOutHorizontally(targetOffsetX = { it })
            SLIDE_OUT_FROM_LEFT -> slideOutHorizontally(targetOffsetX = { -it })
            SLIDE_OUT_FROM_BOTTOM -> slideOutVertically(targetOffsetY = { it })
            else -> slideOutVertically(targetOffsetY = { it })
        }
    ) {
        currentSnackbarData?.let { snackbarData ->
            PienoteSnackbar(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                when {
                                    dragAmount.y > 0 && dragAmount.y > abs(dragAmount.x) ->
                                        slideOutAnimationId = SLIDE_OUT_FROM_BOTTOM

                                    dragAmount.x > 0 ->
                                        slideOutAnimationId = SLIDE_OUT_FROM_RIGHT

                                    dragAmount.x < 0 ->
                                        slideOutAnimationId = SLIDE_OUT_FROM_LEFT
                                }

                                shouldShowSnackbar = false
                            }
                        )
                    },
                snackbarDate = snackbarData
            )
        }
    }
}

/**
 * Calculates the duration based on the text length and having action of the snackbar message.
 *
 * @return The duration in milliseconds.
 */
private fun SnackbarData.calculateDurationBasedOnText(): Long {
    val wordCount = message.trim().split("\\s+".toRegex()).size
    val baseDuration = (wordCount * 500L).coerceAtLeast(3000L)
    return if (action != null) baseDuration + 3000L else baseDuration
}