package com.haghpanh.pienote.features.textEditor.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.PienoteTextToolBar
import com.haghpanh.pienote.commonui.utils.getPrefixOrNull
import com.haghpanh.pienote.commonui.utils.performAction
import com.haghpanh.pienote.features.textEditor.base.Action
import com.haghpanh.pienote.features.textEditor.base.TextEditorValue
import com.haghpanh.pienote.features.textEditor.base.getTextStyle


@Composable
fun PienoteTextEditor(
    value: TextEditorValue,
    modifier: Modifier = Modifier,
) {
    val textFields by remember {
        derivedStateOf { value.getRenderedTexts() }
    }

    val focusManager = LocalFocusManager.current

    // based on the value we decide whether should we move focus down or not.
    var hasAddedSection by remember {
        mutableStateOf(false)
    }

    // keeps focusItem Index to perform adding section based on it.
    var focusedItemIndex: Int by remember {
        mutableIntStateOf(-1)
    }

    LaunchedEffect(key1 = textFields.size) {
        if (hasAddedSection) {
            focusManager.moveFocus(FocusDirection.Down)
            hasAddedSection = false
        }
    }

    Column(modifier = modifier) {
        textFields.forEachIndexed { index, item ->

            // give us bold, code and underline options on actionMenu
            // that appear when selecting a text.
            // todo check
            val textToolbar = buildPienoteTextTool(value = item.second) {
                value.onEachValueChange(index, it)
            }

            CompositionLocalProvider(LocalTextToolbar provides textToolbar) {
                TextEditorField(
                    modifier = Modifier.onFocusEvent {
                        if (it.isFocused) {
                            focusedItemIndex = index
                        }
                    },
                    icon = if (
                        item.first in setOf(
                            Action.TodoListComplete,
                            Action.TodoListNotComplete
                        )
                    ) {
                        {
                            Checkbox(
                                checked = item.first == Action.TodoListComplete,
                                onCheckedChange = { value.onCheckTodo(index) }
                            )
                        }
                    } else {
                        null
                    },
                    value = item.second,
                    onValueChange = { value.onEachValueChange(index, it) },
                    textStyle = item.first?.getTextStyle() ?: TextStyle.Default
                )
            }
        }

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(Action.entries) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = {
                            value.addSection(
                                action = it,
                                index = focusedItemIndex.takeIf { it != -1 }
                            )
                            hasAddedSection = true
                        }
                    ) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

@Composable
private fun TextEditorField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    textStyle: TextStyle = TextStyle.Default,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    icon?.let {
                        icon()
                    }

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

@Composable
private fun buildPienoteTextTool(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
): PienoteTextToolBar {
    return PienoteTextToolBar(
        view = LocalView.current,
        onCustomItemsRequest = if (value.selection.length < 1) {
            null
        } else {
            { menuItem ->
                // perform and action based on which item has been
                // click and returning new text for the selected text
                val result =
                    menuItem.performAction(value.getSelectedText().text)

                // min and max representing exactly what we need here.
                val startRange = value.selection.min
                val endRange = value.selection.max

                // sets new text
                onValueChange(
                    value.copy(
                        text = value.text.replaceRange(
                            range = startRange..<endRange,
                            replacement = result.first
                        )
                    ),
                )

                val newSelectionEndRange by lazy {
                    val length = (menuItem.getPrefixOrNull()?.length ?: 0) * 2

                    if (result.second) {
                        -length
                    } else {
                        length
                    }
                }

                // change selected range based on new text
                onValueChange(
                    value.copy(
                        selection = TextRange(
                            start = startRange,
                            end = endRange + newSelectionEndRange
                        )
                    ),
                )
            }
        }
    )
}
