package com.haghpanh.pienote.commonui.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.haghpanh.pienote.commonui.component.PienoteSnackbar
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlin.math.abs

@Stable
class SnackbarManager {
    private val _currentMessage = Channel<SnackbarData?>(Channel.BUFFERED)
    val currentMessage = _currentMessage.receiveAsFlow()

    suspend fun sendMessage(data: SnackbarData) {
        _currentMessage.send(data)
    }

    suspend fun dismiss() {
        _currentMessage.send(null)
    }

    suspend fun sendError(
        message: String,
        action: (() -> Unit)? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        val snackbarData = SnackbarData(
            message = message,
            type = SnackbarTypes.Error,
            duration = duration,
            action = action?.let {
                SnackbarAction(
                    action = action,
                    label = "Try Again"
                )
            }
        )

        sendMessage(snackbarData)
    }

    suspend fun sendWarning(
        message: String,
        action: (() -> Unit)? = null,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        val snackbarData = SnackbarData(
            message = message,
            type = SnackbarTypes.Warning,
            duration = duration,
            action = if (actionLabel != null && action != null) {
                SnackbarAction(
                    action = action,
                    label = actionLabel
                )
            } else {
                null
            }
        )

        sendMessage(snackbarData)
    }

    suspend fun sendSuccess(
        message: String,
        action: (() -> Unit)? = null,
        actionLabel: String? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        val snackbarData = SnackbarData(
            message = message,
            type = SnackbarTypes.Warning,
            duration = duration,
            action = if (actionLabel != null && action != null) {
                SnackbarAction(
                    action = action,
                    label = actionLabel
                )
            } else {
                null
            }
        )

        sendMessage(snackbarData)
    }
}

@Composable
fun PienoteSnackbarHost(manager: SnackbarManager) {
    var currentSnackbarData: SnackbarData? by remember { mutableStateOf(null) }
    var shouldShowSnackbar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        manager.currentMessage.collectLatest { snackbarData ->
            currentSnackbarData = snackbarData
            shouldShowSnackbar = false

            snackbarData?.let { data ->
                shouldShowSnackbar = true
                val duration = calculateDurationInMillis(data)

                duration?.let {
                    delay(duration)
                    shouldShowSnackbar = false
                }
            }
        }
    }

    var slideOutAnimationId by remember {
        mutableStateOf(0)
    }

    AnimatedVisibility(
        visible = shouldShowSnackbar,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = when (slideOutAnimationId) {
            1 -> slideOutHorizontally(targetOffsetX = { it })
            2 -> slideOutHorizontally(targetOffsetX = { -it })
            else -> slideOutVertically(targetOffsetY = { it })
        }
    ) {
        currentSnackbarData?.let { snackbarData ->
            PienoteSnackbar(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDrag = { _, dragAmount ->
                                if (dragAmount.y > 0 && dragAmount.y > abs(dragAmount.x)) {
                                    slideOutAnimationId = 0
                                    shouldShowSnackbar = false
                                } else if (dragAmount.x > 0) {
                                    slideOutAnimationId = 1
                                    shouldShowSnackbar = false
                                } else if (dragAmount.x < 0) {
                                    slideOutAnimationId = 2
                                    shouldShowSnackbar = false
                                }
                            }
                        )
                    },
                snackbarDate = snackbarData
            )
        }
    }
}

data class SnackbarData(
    val message: String,
    val type: SnackbarTypes,
    val duration: SnackbarDuration = SnackbarDuration.BasedOnMessage,
    val action: SnackbarAction? = null
)

data class SnackbarAction(
    val action: () -> Unit,
    val label: String
)

enum class SnackbarDuration {
    Short,
    Long,
    Infinite,
    BasedOnMessage
}

private fun calculateDurationInMillis(snackbarData: SnackbarData): Long? {
    return when (snackbarData.duration) {
        SnackbarDuration.Short -> 3000L
        SnackbarDuration.Long -> 5000L
        SnackbarDuration.BasedOnMessage -> snackbarData.calculateDurationBasedOnText()
        SnackbarDuration.Infinite -> null
    }
}

private fun SnackbarData.calculateDurationBasedOnText(): Long {
    val message = message
    val countOfWords =
        if (message.isEmpty()) {
            0
        } else {
            message.trim().split("\\s+".toRegex()).size
        }

    return (countOfWords * 500L).let {
        if (action == null && it > 3000L) {
            it
        } else if (action != null) {
            it + 3000L
        } else {
            3000L
        }
    }
}

enum class SnackbarTypes {
    Error,
    Warning,
    Success
}
