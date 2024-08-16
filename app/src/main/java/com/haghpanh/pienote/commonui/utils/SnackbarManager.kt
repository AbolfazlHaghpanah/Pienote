package com.haghpanh.pienote.commonui.utils

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteSnackbar
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject
import kotlin.math.abs

/**
 * A manager class responsible for displaying and dismissing snackbar messages in the application.
 *
 * prefer to inject this class in each screen's viewmodel and pass it to SnackbarHost from viewmodel.
 *
 * @param context The application context for accessing resources.
 */
class SnackbarManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _currentMessage = Channel<SnackbarData?>(Channel.BUFFERED)
    val currentMessage = _currentMessage.receiveAsFlow()

    /**
     * Sends a snackbar message.
     *
     * @param data The data to be displayed in the snackbar.
     */
    suspend fun sendMessage(data: SnackbarData) {
        _currentMessage.send(data)
    }

    /**
     * Dismisses the current snackbar message.
     */
    suspend fun dismiss() {
        _currentMessage.send(null)
    }

    /**
     * Sends an error snackbar message.
     *
     * @param message The error message to be displayed.
     * @param action An optional action to be executed when the snackbar action is clicked.
     * @param duration The duration for which the snackbar should be displayed.
     */
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
                    label = context.getString(R.string.label_try_again)
                )
            }
        )

        sendMessage(snackbarData)
    }

    /**
     * Sends a warning snackbar message.
     *
     * @param message The warning message to be displayed.
     * @param action An optional action to be executed when the snackbar action is clicked.
     * @param actionLabel An optional label for the snackbar action.
     * @param duration The duration for which the snackbar should be displayed.
     */
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

    /**
     * Sends a success snackbar message.
     *
     * @param message The success message to be displayed.
     * @param action An optional action to be executed when the snackbar action is clicked.
     * @param actionLabel An optional label for the snackbar action.
     * @param duration The duration for which the snackbar should be displayed.
     */
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
 * Data class representing the snackbar data.
 *
 * @param message The message to be displayed in the snackbar.
 * @param type The type of the snackbar (Error, Warning, Success).
 * @param duration The duration for which the snackbar should be displayed.
 * @param action An optional action to be executed when the snackbar action is clicked.
 */
@Immutable
data class SnackbarData(
    val message: String,
    val type: SnackbarTypes,
    val duration: SnackbarDuration = SnackbarDuration.BasedOnMessage,
    val action: SnackbarAction? = null
)

/**
 * Data class representing the snackbar action.
 *
 * @param action The action to be executed when the snackbar action is clicked.
 * @param label The label for the snackbar action.
 */
@Immutable
data class SnackbarAction(
    val action: () -> Unit,
    val label: String
)

/**
 * Enum class representing the snackbar duration.
 */
@Immutable
enum class SnackbarDuration {
    Short,
    Long,
    Infinite,
    BasedOnMessage
}

/**
 * Enum class representing the snackbar types.
 */
@Immutable
enum class SnackbarTypes {
    Error,
    Warning,
    Success
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
