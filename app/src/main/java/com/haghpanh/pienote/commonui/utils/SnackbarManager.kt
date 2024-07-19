package com.haghpanh.pienote.commonui.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haghpanh.pienote.commonui.component.PienoteSnackbar
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow

@Stable
class SnackbarManager {
    private val currentMessage = Channel<SnackbarData?>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    suspend fun sendMessage(data: SnackbarData) {
        currentMessage.send(data)
    }

    suspend fun dismiss() {
        currentMessage.send(null)
    }

    @Composable
    fun collectMessageAsState(): State<SnackbarData?> =
        currentMessage.receiveAsFlow().collectAsStateWithLifecycle(initialValue = null)
}

@Composable
fun PienoteSnackbarHost(manager: SnackbarManager) {
    val currentSnackbarData by manager.collectMessageAsState()
    var shouldShowSnackbar by remember {
        mutableStateOf(currentSnackbarData != null)
    }

    LaunchedEffect(currentSnackbarData) {
        shouldShowSnackbar = false

        currentSnackbarData?.let { snackbarData ->
            shouldShowSnackbar = true
            val duration = calculateDurationInMillis(snackbarData)

            duration?.let {
                delay(duration)
                shouldShowSnackbar = false
            }
        }
    }

    AnimatedVisibility(shouldShowSnackbar) {
        currentSnackbarData?.let { snackbarData ->
            PienoteSnackbar(snackbarDate = snackbarData)
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
        SnackbarDuration.BasedOnMessage -> {
            val message = snackbarData.message
            val countOfWords =
                if (message.isEmpty()) {
                    0
                } else {
                    message.trim().split("\\s+".toRegex()).size
                }

            (countOfWords * 500L).let {
                if (snackbarData.action == null && it > 3000L) {
                    it
                } else if (snackbarData.action != null) {
                    it + 3000L
                } else {
                    3000L
                }
            }
        }

        else -> null
    }
}

enum class SnackbarTypes {
    Error,
    Warning,
    Success
}
