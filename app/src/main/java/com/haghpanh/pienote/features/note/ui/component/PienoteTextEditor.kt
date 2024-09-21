package com.haghpanh.pienote.features.note.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.component.PienoteTextField
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.commonui.utils.PienoteTextToolBar
import com.haghpanh.pienote.commonui.utils.getPrefixOrNull
import com.haghpanh.pienote.commonui.utils.performAction

@Composable
fun PienoteTextEditor(
    value: TextFieldValue,
    onValueChange: (TextFieldValue, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val textEditorValue = remember {
        TextEditorValue(value.text)
    }

    DisposableEffect(textEditorValue) {
        onDispose {
            onValueChange(
                value.copy(
                    annotatedString = textEditorValue.getAnnotatedText(),
                ),
                textEditorValue.markdownInput
            )
        }
    }

    // give us bold, code and underline options on actionMenu
    // that appear when selecting a text.
    val textToolbar = PienoteTextToolBar(
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
                    textEditorValue.markdownInput
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
                    textEditorValue.markdownInput
                )
            }
        }
    )

    CompositionLocalProvider(LocalTextToolbar provides textToolbar) {
        Column(modifier = modifier) {
            textEditorValue.getRenderedTexts().forEachIndexed { index, item ->
                PienoteTextField(
                    value = item,
                    onValueChange = { textEditorValue.onValueChange(it, index) },
                    textStyle = PienoteTheme.typography.titleMedium
                )
            }

            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(TextEditorValue.Companion.Action.entries) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        TextButton(onClick = { textEditorValue.add(it) }) {
                            Text(text = it.name)
                        }
                    }
                }
            }
        }
    }
}

class TextEditorValue(
    initialText: String? = null
) {
    private val renderedTexts = mutableStateListOf<String>().also { list ->
        if (!initialText.isNullOrEmpty()) {
            initialText.split("\n\n")
                .filter { it.isNotBlank() }
                .forEach {
                    list.add(it)
                }
        }
    }

    var markdownInput: String = initialText.orEmpty()
        private set

    fun getRenderedTexts(): List<String> = renderedTexts

    fun add(action: Action) {
        renderedTexts.add("${action.key} ")

        markdownInput += "\n\n${action.key} "
    }

    fun onValueChange(value: String, index: Int) {
        renderedTexts[index] = value

        markdownInput = markdownInput
            .split("\n\n")
            .mapIndexed { textIndex, s ->
                if (textIndex == index) {
                    value
                } else {
                    s
                }
            }.joinToString(separator = "") { "$it\n\n" }
    }

    fun getAnnotatedText(): AnnotatedString {
        return buildAnnotatedString {
            var counter = 0
            renderedTexts.forEach { text ->
                text.removePrefix().let { renderedText ->
                    append(renderedText)

                    text.getActionOrNull()?.let { action ->
                        addStyle(
                            start = counter, end = counter + renderedText.length,
                            style = action.getFontStyle().toSpanStyle()
                        )
                    }

                    counter += renderedText.length
                }
            }
        }
    }

    companion object {
        private val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))

        enum class Action(val key: String) {
            H1("#"),
            H2("##"),
            H3("###"),
            H4("####"),
            TodoListNotComplete("- [ ]"),
            TodoListComplete("- [x]"),
            DotList("-"),
            NumberList(".1")
        }

        private fun Action.getFontStyle(): TextStyle {
            return when (this) {
                Action.H1 -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Action.H2 -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Action.H3 -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Action.H4 -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )

                Action.TodoListNotComplete -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.None
                )

                Action.TodoListComplete -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    textDecoration = TextDecoration.LineThrough
                )

                Action.DotList -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )

                Action.NumberList -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }

        private fun String.getActionOrNull(): Action? {
            Action.entries.reversed().forEach { action ->
                if (this.startsWith(action.key)) {
                    return action
                }
            }
            return null
        }

        private fun String.removePrefix(): String {
            Action.entries.reversed().forEach { action ->
                if (this.startsWith("${action.key} ")) {
                    return this.removePrefix("${action.key} ")
                }
            }
            return this
        }

        fun renderMarkdownToAnnotatedString(markdownInput: String): AnnotatedString {
            return buildAnnotatedString {
                var counter = 0
                markdownInput
                    .split("\n\n")
                    .forEach { text ->
                        text.removePrefix().let { renderedText ->
                            append(renderedText + "\n")

                            text.getActionOrNull()?.let { action ->
                                addStyle(
                                    start = counter, end = counter + renderedText.length,
                                    style = action.getFontStyle().toSpanStyle()
                                )
                            }

                            counter += renderedText.length + 1
                        }
                    }
            }
        }
    }
}