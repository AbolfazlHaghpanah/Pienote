package com.haghpanh.pienote.features.note.ui.component

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.haghpanh.pienote.features.note.ui.component.TextEditorValue.Companion.getTextStyle

@Composable
fun PienoteTextEditor(
    value: TextEditorValue,
    modifier: Modifier = Modifier,
) {
    val textFields by remember {
        derivedStateOf { value.getRenderedTexts() }
    }

    LaunchedEffect(key1 = textFields) {
        Log.d(
            "mmd",
            "PienoteTextEditor: ${textFields.joinToString { it.second.getSelectedText() }}"
        )
    }

    Column(modifier = modifier) {
        textFields.forEachIndexed { index, item ->

            // give us bold, code and underline options on actionMenu
            // that appear when selecting a text.
            val textToolbar = buildPienoteTextTool(value = item.second) {
                value.onEachValueChange(index, it)
            }

            CompositionLocalProvider(LocalTextToolbar provides textToolbar) {
                PienoteTextField(
                    value = item.second,
                    onValueChange = { value.onEachValueChange(index, it) },
                    textStyle = item.first?.getTextStyle() ?: PienoteTheme.typography.titleMedium
                )
            }
        }

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(TextEditorValue.Companion.Action.entries) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { value.addSection(it) }) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

class TextEditorValue(
    initialMarkdown: String = "",
) {
    var markdown by mutableStateOf(initialMarkdown)
        private set
    var annotatedString by mutableStateOf(renderMarkdownToAnnotatedString(markdown))
        private set

    private val renderedTexts = mutableStateListOf<Pair<Action?, TextFieldValue>>().also { list ->
        if (initialMarkdown.isNotEmpty()) {
            initialMarkdown.split("\n\n")
                .filter { it.isNotBlank() }
                .forEach {
                    val text = it.removePrefix()
                    val action = it.getActionOrNull()
                    list.add(action to TextFieldValue(text))
                }
        }
    }

    fun getRenderedTexts(): List<Pair<Action?, TextFieldValue>> =
        renderedTexts.map { it.first to it.second }

    fun addSection(action: Action) {
        renderedTexts.add(action to TextFieldValue())
    }

    fun onEachValueChange(index: Int, value: TextFieldValue) {
        Log.d("mmd", "onEachValueChange: $index + $value")
        renderedTexts[index] = renderedTexts[index].copy(second = value)
    }

    fun updateMarkdown(value: String) {
        markdown = value
        syncRenderedTextsWithMarkdown()
        syncAnnotatedStringWithRenderedTexts()
    }

    fun await(): TextEditorValue {
        syncAnnotatedStringWithRenderedTexts()
        syncMarkdownWithRenderedText()
        return this
    }

    private fun syncRenderedTextsWithMarkdown() {
        renderedTexts.removeAll { true }
        if (markdown.isNotEmpty()) {
            markdown.split("\n\n")
                .filter { it.isNotBlank() }
                .forEach {
                    val text = it.removePrefix()
                    val action = it.getActionOrNull()
                    renderedTexts.add(action to TextFieldValue(text))
                }
        }
    }

    private fun syncAnnotatedStringWithRenderedTexts() {
        annotatedString = buildAnnotatedString {
            var count = 0
            renderedTexts.forEach { renderedText ->
                append(renderedText.second.text + "\n")

                renderedText.first?.getTextStyle()?.let { textStyle ->
                    addStyle(
                        start = count,
                        end = count + renderedText.second.text.length,
                        style = textStyle.toSpanStyle()
                    )
                }

                count += (renderedText.second.text.length + 1)
            }
        }
    }

    private fun syncMarkdownWithRenderedText() {
        emptyMarkdown()
        renderedTexts.forEach {
            markdown += "${it.first?.key}${it.second.text}\n\n"
        }
    }

    private fun emptyMarkdown() {
        markdown = ""
    }

    companion object {
        private val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))

        enum class Action(val key: String) {
            Non(""),
            H1("# "),
            H2("## "),
            H3("### "),
            H4("#### "),
            TodoListNotComplete("- [ ] "),
            TodoListComplete("- [x] "),
            DotList("- "),

            // TODO: find the way
            NumberList(".1 ")
        }

        fun Action.getTextStyle(): TextStyle {
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

                Action.Non -> TextStyle.Default.copy(
                    fontFamily = robotoRegularFont,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light
                )
            }
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
                                    start = counter,
                                    end = counter + renderedText.length,
                                    style = action.getTextStyle().toSpanStyle()
                                )
                            }

                            counter += renderedText.length + 1
                        }
                    }
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
                if (this.startsWith(action.key)) {
                    return this.removePrefix(action.key)
                }
            }
            return this
        }
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

@Composable
fun rememberTextEditorValue(
    initialMarkdown: String
): TextEditorValue {
    return remember {
        TextEditorValue(initialMarkdown)
    }
}
