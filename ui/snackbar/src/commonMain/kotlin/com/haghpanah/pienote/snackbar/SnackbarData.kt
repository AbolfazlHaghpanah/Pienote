package com.haghpanah.pienote.snackbar

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.CoroutineScope
import org.jetbrains.compose.resources.StringResource

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
    val action: CoroutineScope.() -> Unit,
    val label: StringResource
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
