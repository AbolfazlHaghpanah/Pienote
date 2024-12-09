package com.haghpanah.pienote.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.text.TextStyle
import com.haghpanah.pienote.shortcuthandler.ShortcutEvent
import com.haghpanah.pienote.shortcuthandler.ShortcutType
import com.haghpanah.pienote.shortcuthandler.addKeyboardShortcut
import com.haghpanah.pienote.utils.CreateIcon
import com.haghpanah.pienote.utils.TextEditorAction
import com.haghpanah.pienote.utils.TextEditorValue
import com.haghpanah.pienote.utils.getPlaceHolderStringId
import com.haghpanah.pienote.utils.getTextStyle
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PienoteTextEditor(
    value: TextEditorValue,
    shouldShowEditingOptions: Boolean,
    modifier: Modifier = Modifier,
    textFieldModifier: Modifier = Modifier,
    onFocusItemIndexChanged: ((Int?) -> Unit)? = null,
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val textFields by remember {
        derivedStateOf { value.getRenderedTexts() }
    }

    val focusRequesters by remember {
        derivedStateOf {
            List(textFields.size) {
                FocusRequester()
            }
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
            focusRequesters[focusedItemIndex?.plus(1) ?: focusRequesters.lastIndex].requestFocus()
            hasAddedSection = false
        }

        if (hasRemovedSection) {
            focusRequesters[focusedItemIndex?.minus(1) ?: focusRequesters.lastIndex].requestFocus()
            hasRemovedSection = false
        }
    }

    onFocusItemIndexChanged?.let { onChange ->
        LaunchedEffect(focusedItemIndex) {
            onChange(focusedItemIndex)
        }
    }

    addKeyboardShortcut(
        ShortcutEvent(Key.DirectionUp)
    ) {
        focusedItemIndex?.let {
            if (it != 0) {
                focusRequesters[it.minus(1)].requestFocus()
            }
        }
        true
    }

    addKeyboardShortcut(
        Key.DirectionDown
    ) {
        focusedItemIndex?.let {
            if (it != textFields.lastIndex) {
                focusRequesters[it.plus(1)].requestFocus()
            }
        }
        true
    }

    setOf(
        ShortcutEvent(Key.Backspace, ShortcutType.WithShift),
        ShortcutEvent(Key.Backspace)
    ).forEach {
        addKeyboardShortcut(it) {
            val focusedItem = textFields[focusedItemIndex ?: textFields.lastIndex]
            if (
                focusedItem.action in setOf(
                    TextEditorAction.TodoListComplete,
                    TextEditorAction.TodoListNotComplete,
                    TextEditorAction.List
                ) && focusedItem.value.text.isEmpty()
            ) {
                value.updateAction(focusedItemIndex ?: textFields.lastIndex, TextEditorAction.Non)
            } else if (focusedItem.value.text.isEmpty()) {
                if (focusedItemIndex == 0 && textFields.size > 1) {
                    value.removeSection(0)
                } else if (focusedItemIndex != 0) {
                    value.removeSection(focusedItemIndex ?: textFields.lastIndex)
                    hasRemovedSection = true
                }
            }
            true
        }
    }

    setOf(
        ShortcutEvent(Key.Enter, ShortcutType.WithShift),
        ShortcutEvent(Key.Enter)
    ).forEach {
        addKeyboardShortcut(it) {
            val focusedItem = textFields[focusedItemIndex ?: textFields.lastIndex]

            val action = when (focusedItem.action) {
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
            true
        }
    }

    Column(modifier = modifier) {
        textFields.forEachIndexed { index, item ->
            TextEditorField(
                modifier = textFieldModifier
                    .focusRequester(focusRequesters[index])
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .fillMaxWidth()
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
                textStyle = item.action?.getTextStyle() ?: TextStyle.Default,
                placeHolderText = if (shouldShowEditingOptions) {
                    item.action
                        ?.getPlaceHolderStringId()
                        ?.let { stringResource(it) }
                } else {
                    null
                },
            )

//                DropdownMenu(
//                    expanded = shouldShowEditingOptions && updatingItemIndex == index,
//                    onDismissRequest = {
//                        updatingItemIndex = null
//                    },
//                    properties = PopupProperties(),
//                ) {
//                    DropdownMenuItem(
//                        text = { Text(text = stringResource(R.string.label_change)) },
//                        onClick = { },
//                        enabled = false
//                    )
//
//                    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
//
//                    Column(
//                        modifier = Modifier
//                            .heightIn(max = 160.dp)
//                            .verticalScroll(rememberScrollState())
//                    ) {
//                        TextEditorAction.entries
//                            .filter {
//                                it !in setOf(
//                                    TodoListComplete,
//                                    OrderedList
//                                )
//                            }.forEach { action ->
//                                DropdownMenuItem(
//                                    text = {
//                                        action.getFullNameStringId()?.let {
//                                            Text(text = stringResource(id = it))
//                                        }
//                                    },
//                                    onClick = {
//                                        value.updateAction(
//                                            newAction = action,
//                                            index = index
//                                        )
//                                        hasAddedSection = true
//                                        updatingItemIndex = null
//                                    }
//                                )
//                            }
//                    }
//                }
        }
    }
}
