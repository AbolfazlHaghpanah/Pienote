package pienote.commonui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pienote.commonui.theme.PienoteTheme
import pienote.commonui.utils.SnackbarAction
import pienote.commonui.utils.SnackbarData
import pienote.commonui.utils.SnackbarDuration
import pienote.commonui.utils.SnackbarTypes

@Composable
fun PienoteSnackbar(
    snackbarDate: SnackbarData,
    modifier: Modifier = Modifier,
    dismissAction: @Composable (() -> Unit)? = null
) {
    Snackbar(
        modifier = modifier.padding(24.dp),
        action = snackbarDate
            .action
            ?.let {
                {
                    TextButton(
                        modifier = Modifier.padding(vertical = 4.dp),
                        onClick = it.action,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = when (snackbarDate.type) {
                                SnackbarTypes.Error -> PienoteTheme.colors.errorContainer
                                SnackbarTypes.Warning -> PienoteTheme.colors.secondaryContainer
                                SnackbarTypes.Success -> PienoteTheme.colors.tertiaryContainer
                            }
                        )
                    ) {
                        Text(text = it.label)
                    }
                }
            },
        dismissAction = dismissAction,
    ) {
        Text(text = snackbarDate.message)
    }
}

@Preview
@Composable
private fun PienoteSnackbarPreview() {
    PienoteTheme {
        PienoteSnackbar(
            snackbarDate = SnackbarData(
                message = "Succesfully to merge all Problems ",
                type = SnackbarTypes.Warning,
                duration = SnackbarDuration.BasedOnMessage,
                action = SnackbarAction(
                    {},
                    "Awesome"
                )
            )
        )
    }
}
