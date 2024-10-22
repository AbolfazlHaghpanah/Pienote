package pienote.commonui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import pienote.commonui.theme.PienoteTheme

@Composable
fun PienoteTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PienoteTheme.typography.displaySmall,
    placeHolderText: String? = null,
    contentColor: Color = PienoteTheme.colors.onBackground
) {
    CompositionLocalProvider(
        value = LocalContentColor provides contentColor
    ) {
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle.copy(color = contentColor),
            cursorBrush = SolidColor(contentColor),
            decorationBox = { content ->
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (value.isEmpty() && placeHolderText != null) {
                        Text(
                            text = placeHolderText,
                            color = LocalContentColor.current.copy(alpha = 0.6f),
                            style = textStyle
                        )
                    }

                    content()
                }
            }
        )
    }
}

@Composable
fun PienoteTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PienoteTheme.typography.displaySmall,
    placeHolderText: String? = null,
    contentColor: Color = PienoteTheme.colors.onBackground
) {
    CompositionLocalProvider(
        value = LocalContentColor provides contentColor
    ) {
        BasicTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle.copy(color = contentColor),
            cursorBrush = SolidColor(contentColor),
            decorationBox = { content ->
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (value.text.isEmpty() && placeHolderText != null) {
                        Text(
                            text = placeHolderText,
                            color = LocalContentColor.current.copy(alpha = 0.6f),
                            style = textStyle
                        )
                    }

                    content()
                }
            }
        )
    }
}
