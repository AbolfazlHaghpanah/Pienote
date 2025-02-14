package com.haghpanah.pienote.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.haghpanah.pienote.utils.CreateIcon
import com.haghpanah.pienote.utils.PienoteTextToolBar
import com.haghpanah.pienote.utils.TextEditorAction
import com.haghpanah.pienote.utils.TextEditorValue
import com.haghpanah.pienote.utils.getFullNameStringId
import com.haghpanah.pienote.utils.getPlaceHolderStringId
import com.haghpanah.pienote.utils.getPrefixOrNull
import com.haghpanah.pienote.utils.getTextStyle
import com.haghpanah.pienote.utils.performAction
import org.jetbrains.compose.resources.stringResource
import pienote.ui.texteditor.generated.resources.Res
import pienote.ui.texteditor.generated.resources.label_change

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PienoteTextEditor(
    value: TextEditorValue,
    shouldShowEditingOptions: Boolean,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onFocusItemIndexChanged: ((Int?) -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val textFields by remember {
        derivedStateOf {
            value.getRenderedTexts()
        }
    }
    var updatingItemIndex: Int? by rememberSaveable {
        mutableStateOf(null)
    }

    // based on the value we decide whether should we move focus down or not.
    var hasAddedSection by rememberSaveable {
        mutableStateOf(false)
    }
    // based on the value we decide whether should we move focus up or not.
    var hasRemovedSection by rememberSaveable {
        mutableStateOf(false)
    }
    // keeps focusItem Index to perform adding section based on it.
    var focusedItemIndex: Int? by rememberSaveable {
        mutableStateOf(null)
    }

    LaunchedEffect(shouldShowEditingOptions) {
        if (!shouldShowEditingOptions) {
            focusedItemIndex = null
        }
    }

    // handles delay between adding a section and showing it on textField for focus on
    LaunchedEffect(textFields.size) {
        if (hasAddedSection) {
            focusManager.moveFocus(FocusDirection.Next)
            hasAddedSection = false
        }

        if (hasRemovedSection) {
            focusManager.moveFocus(FocusDirection.Previous)
            hasRemovedSection = false
        }
    }

    onFocusItemIndexChanged?.let { onChange ->
        LaunchedEffect(focusedItemIndex) {
            onChange(focusedItemIndex)
        }
    }

    Column(modifier = modifier) {
        textFields.forEachIndexed { index, item ->
            Box {
                TextEditorField(
                    modifier = textFieldModifier
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .fillMaxWidth()
                        .onKeyEvent {
                            if (it.key == Key.Backspace) {
                                if (
                                    item.action in setOf(
                                        TextEditorAction.TodoListComplete,
                                        TextEditorAction.TodoListNotComplete,
                                        TextEditorAction.List
                                    ) && item.value.text.isEmpty()
                                ) {
                                    value.updateAction(index, TextEditorAction.Non)
                                } else if (item.value.text.isEmpty() && index != 0) {
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
                                    TextEditorAction.TodoListNotComplete,
                                    TextEditorAction.TodoListComplete
                                )
                            ) {
                                value.onCheckTodo(index)
                            }
                        }
                    },
                    value = item.value,
                    onValueChange = { value.onEachValueChange(index, it) },
                    onUpdateClick = if (focusedItemIndex == index && shouldShowEditingOptions) {
                        { updatingItemIndex = index }
                    } else {
                        null
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardAction = KeyboardActions(
                        onDone = {
                            val action = when (item.action) {
                                TextEditorAction.TodoListComplete,
                                TextEditorAction.TodoListNotComplete -> TextEditorAction.TodoListNotComplete

                                TextEditorAction.List -> TextEditorAction.List
                                else -> TextEditorAction.Non
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
                            ?.let { stringResource(it) }
                    } else {
                        null
                    }
                )

                DropdownMenu(
                    expanded = shouldShowEditingOptions && updatingItemIndex == index,
                    onDismissRequest = {
                        updatingItemIndex = null
                    },
                    properties = PopupProperties(),
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(Res.string.label_change)) },
                        onClick = { },
                        enabled = false
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))

                    Column(
                        modifier = Modifier
                            .heightIn(max = 160.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        TextEditorAction.entries
                            .filter {
                                it !in setOf(
                                    TextEditorAction.TodoListComplete,
                                    TextEditorAction.OrderedList
                                )
                            }.forEach { action ->
                                DropdownMenuItem(
                                    text = {
                                        action.getFullNameStringId()?.let {
                                            Text(text = stringResource(it))
                                        }
                                    },
                                    onClick = {
                                        value.updateAction(
                                            newAction = action,
                                            index = index
                                        )
                                        hasAddedSection = true
                                        updatingItemIndex = null
                                    }
                                )
                            }
                    }
                }
            }
        }
    }
}

// TODO Use it to customize TextToolBar
@Suppress("UnusedPrivateMember")
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