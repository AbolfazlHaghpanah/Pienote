package pienote.commonui.texteditor.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.haghpanh.pienote.R
import pienote.commonui.texteditor.utils.CreateIcon
import pienote.commonui.texteditor.utils.TextEditorAction
import pienote.commonui.texteditor.utils.TextEditorAction.List
import pienote.commonui.texteditor.utils.TextEditorAction.Non
import pienote.commonui.texteditor.utils.TextEditorAction.OrderedList
import pienote.commonui.texteditor.utils.TextEditorAction.TodoListComplete
import pienote.commonui.texteditor.utils.TextEditorAction.TodoListNotComplete
import pienote.commonui.texteditor.utils.TextEditorValue
import pienote.commonui.texteditor.utils.getFullNameStringId
import pienote.commonui.texteditor.utils.getPlaceHolderStringId
import pienote.commonui.texteditor.utils.getTextStyle
import pienote.commonui.theme.PienoteTheme
import pienote.commonui.utils.PienoteTextToolBar
import pienote.commonui.utils.getPrefixOrNull
import pienote.commonui.utils.performAction

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
        derivedStateOf { value.getRenderedTexts() }
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
                                        TodoListComplete,
                                        TodoListNotComplete,
                                        List
                                    ) && item.value.text.isEmpty()
                                ) {
                                    value.updateAction(index, Non)
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
                        { updatingItemIndex = index }
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

                DropdownMenu(
                    expanded = shouldShowEditingOptions && updatingItemIndex == index,
                    onDismissRequest = {
                        updatingItemIndex = null
                    },
                    properties = PopupProperties(),
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.label_change)) },
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
                                    TodoListComplete,
                                    OrderedList
                                )
                            }.forEach { action ->
                                DropdownMenuItem(
                                    text = {
                                        action.getFullNameStringId()?.let {
                                            Text(text = stringResource(id = it))
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
                        label = "show change icon"
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
