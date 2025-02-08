package com.haghpanah.pienote.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun PienoteSnackbar(
    snackbarDate: SnackbarData,
    modifier: Modifier = Modifier,
    dismissAction: @Composable (() -> Unit)? = null,
) {
    Snackbar(
        modifier = modifier.padding(24.dp),
        action = snackbarDate
            .action
            ?.let {
                {
                    val scope = rememberCoroutineScope()

                    TextButton(
                        modifier = Modifier.padding(vertical = 4.dp),
                        onClick = {
                            it.action.invoke(scope)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = when (snackbarDate.type) {
                                SnackbarTypes.Error -> PienoteTheme.colors.errorContainer
                                SnackbarTypes.Warning -> PienoteTheme.colors.secondaryContainer
                                SnackbarTypes.Success -> PienoteTheme.colors.tertiaryContainer
                            }
                        )
                    ) {
                        Text(
                            text = stringResource(it.label)
                        )
                    }
                }
            },
        dismissAction = dismissAction,
    ) {
        Text(text = snackbarDate.message)
    }
}
