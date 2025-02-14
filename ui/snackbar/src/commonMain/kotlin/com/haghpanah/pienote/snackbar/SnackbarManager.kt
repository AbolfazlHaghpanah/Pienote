package com.haghpanah.pienote.snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.jetbrains.compose.resources.StringResource
import pienote.ui.snackbar.generated.resources.Res
import pienote.ui.snackbar.generated.resources.label_try_again

/**
 * A manager class responsible for displaying and dismissing snackbar messages in the application.
 *
 * prefer to inject this class in each screen's viewmodel and pass it to SnackbarHost from viewmodel.
 */
class SnackbarManager {
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
        action: (CoroutineScope.() -> Unit)? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        val snackbarData = SnackbarData(
            message = message,
            type = SnackbarTypes.Error,
            duration = duration,
            action = action?.let {
                SnackbarAction(
                    action = action,
                    label = Res.string.label_try_again
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
        action: (CoroutineScope.() -> Unit)? = null,
        actionLabel: StringResource? = null,
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
        action: (CoroutineScope.() -> Unit)? = null,
        actionLabel: StringResource? = null,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        val snackbarData = SnackbarData(
            message = message,
            type = SnackbarTypes.Success,
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
