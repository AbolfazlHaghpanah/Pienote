package com.haghpanh.pienote.features.texteditor.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue

class TextEditorValue(
    initialMarkdown: String = "",
) {
    var markdown by mutableStateOf(initialMarkdown)
        private set
    var annotatedString by mutableStateOf(renderMarkdownToAnnotatedString(markdown))
        private set

    private val renderedTexts = mutableStateListOf<Pair<TextEditorAction?, TextFieldValue>>().also { list ->
        if (initialMarkdown.isNotEmpty()) {
            initialMarkdown.split("\n\n")
                .filter { it.isNotBlank() }
                .forEach {
                    val text = it.removePrefix()
                    val action = it.getActionOrNull()
                    list.add(action to TextFieldValue(text))
                }
        } else {
            list.add(TextEditorAction.Non to TextFieldValue())
        }
    }

    fun getRenderedTexts(): List<Pair<TextEditorAction?, TextFieldValue>> =
        renderedTexts.map { it.first to it.second }

    fun addSection(action: TextEditorAction, index: Int? = null) {
        if (index != null) {
            renderedTexts.add(index + 1, action to TextFieldValue())
        } else {
            renderedTexts.add(action to TextFieldValue())
        }
    }

    fun updateAction(index: Int, newAction: TextEditorAction) {
        renderedTexts[index] = renderedTexts[index].copy(first = newAction)
    }

    fun onEachValueChange(index: Int, value: TextFieldValue) {
        renderedTexts[index] = renderedTexts[index].copy(second = value)
    }

    fun onCheckTodo(index: Int) {
        val renderedText = renderedTexts[index]

        if (renderedText.first !in setOf(
                TextEditorAction.TodoListComplete,
                TextEditorAction.TodoListNotComplete
            )
        ) {
            return
        }

        val newValue = renderedText.copy(
            first = if (renderedText.first == TextEditorAction.TodoListComplete) {
                TextEditorAction.TodoListNotComplete
            } else {
                TextEditorAction.TodoListComplete
            }
        )

        renderedTexts[index] = newValue
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
                append(renderedText.second.text)

                renderedText.first?.getTextStyle()?.let { textStyle ->
                    addStyle(
                        start = count,
                        end = count + renderedText.second.text.length,
                        style = textStyle.toSpanStyle()
                    )

                    addStyle(
                        start = count,
                        end = count + renderedText.second.text.length,
                        style = textStyle.toParagraphStyle()
                    )
                }

                count += (renderedText.second.text.length)
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
        val saver = Saver<TextEditorValue, String>(
            save = { value ->
                value.markdown
            },
            restore = { markdown ->
                TextEditorValue(markdown)
            }
        )
    }
}

@Composable
fun rememberTextEditorValue(
    initialMarkdown: String
): TextEditorValue {
    return rememberSaveable(saver = TextEditorValue.saver) {
        TextEditorValue(initialMarkdown)
    }
}
