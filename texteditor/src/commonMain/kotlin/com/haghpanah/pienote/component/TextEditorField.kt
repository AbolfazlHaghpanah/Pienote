package com.haghpanah.pienote.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
internal fun TextEditorField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit = {},
    onUpdateClick: (() -> Unit)? = null,
    textStyle: TextStyle = TextStyle.Default,
    placeHolderText: String? = null,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardAction: KeyboardActions = KeyboardActions.Default
) {
    CompositionLocalProvider(
        value = LocalContentColor provides contentColor
    ) {
        BasicTextField(
            modifier = modifier.padding(end = 30.dp),
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle.copy(color = contentColor),
            cursorBrush = SolidColor(contentColor),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardAction,
            decorationBox = { content ->
                Row(
                    modifier = Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    AnimatedContent(
                        targetState = onUpdateClick != null,
                        transitionSpec = { fadeIn().togetherWith(fadeOut()) },
                        label = "show change icon"
                    ) {
                        if (it) {
                            Icon(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .size(30.dp)
                                    .padding(4.dp)
                                    .clickable { onUpdateClick?.invoke() },
                                tint = contentColor.copy(alpha = 0.3f),
                                imageVector = Icons.Rounded.Menu,
                                contentDescription = null
                            )
                        } else {
                            Spacer(modifier = Modifier.size(30.dp))
                        }
                    }

                    icon()

                    Box {
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
            },
            interactionSource = null
        )
    }
}
