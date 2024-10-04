package com.haghpanh.pienote.features.texteditor.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.PienoteTextToolBar
import com.haghpanh.pienote.commonui.utils.getPrefixOrNull
import com.haghpanh.pienote.commonui.utils.performAction
import com.haghpanh.pienote.features.texteditor.utils.CreateIcon
import com.haghpanh.pienote.features.texteditor.utils.TextEditorAction
import com.haghpanh.pienote.features.texteditor.utils.TextEditorAction.List
import com.haghpanh.pienote.features.texteditor.utils.TextEditorAction.Non
import com.haghpanh.pienote.features.texteditor.utils.TextEditorAction.TodoListComplete
import com.haghpanh.pienote.features.texteditor.utils.TextEditorAction.TodoListNotComplete
import com.haghpanh.pienote.features.texteditor.utils.TextEditorValue
import com.haghpanh.pienote.features.texteditor.utils.getNameStringId
import com.haghpanh.pienote.features.texteditor.utils.getPlaceHolderStringId
import com.haghpanh.pienote.features.texteditor.utils.getTextStyle

@Composable
fun PienoteTextEditor(
    value: TextEditorValue,
    shouldShowEditingOptions: Boolean,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
) {
    val textFields by remember {
        derivedStateOf { value.getRenderedTexts() }
    }
    var updatingItemIndex: Int? by rememberSaveable {
        mutableStateOf(null)
    }

    val focusManager = LocalFocusManager.current

    // based on the value we decide whether should we move focus down or not.
    var hasAddedSection by rememberSaveable {
        mutableStateOf(false)
    }
    // based on the value we decide whether should we move focus up or not.
    var hasRemovedSection by rememberSaveable {
        mutableStateOf(false)
    }
    // keeps focusItem Index to perform adding section based on it.
    var focusedItemIndex: Int by rememberSaveable {
        mutableIntStateOf(-1)
    }

    LaunchedEffect(textFields.size) {
        if (hasAddedSection) {
            focusManager.moveFocus(FocusDirection.Down)
            hasAddedSection = false
        }

        if (hasRemovedSection) {
            focusManager.moveFocus(FocusDirection.Up)
            hasRemovedSection = false
        }
    }

    Column(modifier = modifier) {
        textFields.forEachIndexed { index, item ->
            // give us bold, code and underline options on actionMenu
            // that appear when selecting a text.
            // todo check
            val textToolbar = buildPienoteTextTool(value = item.value) {
                value.onEachValueChange(index, it)
            }

            CompositionLocalProvider(LocalTextToolbar provides textToolbar) {
                TextEditorField(
                    modifier = textFieldModifier
                        .fillMaxWidth()
                        .onKeyEvent {
                            if (it.key == Key.Backspace) {
                                if (
                                    item.action in setOf(
                                        TodoListComplete,
                                        TodoListNotComplete,
                                        List
                                    ) && item.value.text.isEmpty()
                                ) {
                                    value.updateAction(index, Non)
                                } else if (item.value.text.isEmpty()) {
                                    value.removeSection(index)
                                    hasRemovedSection = true
                                }
                            }
                            false
                        }
                        .onFocusEvent {
                            if (it.isFocused) {
                                focusedItemIndex = index
                            }
                        },
                    icon = {
                        item.action?.CreateIcon {
                            if (item.action in setOf(
                                    TodoListNotComplete,
                                    TodoListComplete
                                )
                            ) {
                                value.onCheckTodo(index)
                            }
                        }
                    },
                    value = item.value,
                    onValueChange = { value.onEachValueChange(index, it) },
                    onUpdateClick = if (focusedItemIndex == index && shouldShowEditingOptions) {
                        {
                            updatingItemIndex = index
                        }
                    } else {
                        null
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardAction = KeyboardActions(
                        onDone = {
                            val action = when (item.action) {
                                TodoListComplete,
                                TodoListNotComplete -> TodoListNotComplete

                                List -> List
                                else -> Non
                            }

                            value.addSection(
                                action = action,
                                index = focusedItemIndex.takeIf { it != -1 }
                            )

                            hasAddedSection = true
                        }
                    ),
                    textStyle = item.action?.getTextStyle() ?: TextStyle.Default,
                    placeHolderText = if (shouldShowEditingOptions) {
                        item.action
                            ?.getPlaceHolderStringId()
                            ?.let { stringResource(id = it) }
                    } else {
                        null
                    }
                )

                AnimatedVisibility(visible = shouldShowEditingOptions && updatingItemIndex == index) {
                    LazyRow(
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 30.dp)
                            .clip(PienoteTheme.shapes.rounded)
                            .background(PienoteTheme.colors.surface)
                            .fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        items(
                            items = TextEditorAction.entries
                                .filter {
                                    it !in setOf(
                                        TodoListComplete
                                    )
                                }
                        ) { action ->
                            Box(modifier = Modifier.fillMaxWidth()) {
                                TextButton(
                                    onClick = {
                                        value.updateAction(
                                            newAction = action,
                                            index = index
                                        )
                                        hasAddedSection = true
                                        updatingItemIndex = null
                                    }
                                ) {
                                    action.getNameStringId()?.let {
                                        Text(text = stringResource(id = it))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(visible = shouldShowEditingOptions && updatingItemIndex == null) {
            LazyRow(
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .clip(PienoteTheme.shapes.rounded)
                    .background(PienoteTheme.colors.surface)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                items(
                    items = TextEditorAction.entries.filter {
                        it !in setOf(
                            TodoListComplete
                        )
                    }
                ) { action ->
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(
                            onClick = {
                                value.addSection(
                                    action = action,
                                    index = focusedItemIndex.takeIf { it != -1 }
                                )
                                hasAddedSection = true
                            }
                        ) {
                            action.getNameStringId()?.let {
                                Text(text = stringResource(id = it))
                            }
                        }
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
    icon: @Composable () -> Unit = {},
    onUpdateClick: (() -> Unit)? = null,
    textStyle: TextStyle = TextStyle.Default,
    placeHolderText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardAction: KeyboardActions = KeyboardActions.Default,
    contentColor: Color = PienoteTheme.colors.onBackground
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
                        label = "show "
                    ) {
                        if (it) {
                            Icon(
                                modifier = Modifier
                                    .clip(PienoteTheme.shapes.verySmall)
                                    .size(30.dp)
                                    .padding(4.dp)
                                    .clickable { onUpdateClick?.invoke() },
                                tint = PienoteTheme.colors.onBackground.copy(alpha = 0.3f),
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
