package com.haghpanh.pienote.features.textEditor.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

    fun addSection(action: Action, index: Int? = null) {
        if (index != null) {
            renderedTexts.add(index + 1, action to TextFieldValue())
        } else {
            renderedTexts.add(action to TextFieldValue())
        }
    }

    fun onEachValueChange(index: Int, value: TextFieldValue) {
        renderedTexts[index] = renderedTexts[index].copy(second = value)
    }

    fun onCheckTodo(index: Int) {
        val renderedText = renderedTexts[index]

        if (renderedText.first !in setOf(Action.TodoListComplete, Action.TodoListNotComplete)) return

        val newValue = renderedText.copy(
            first = if (renderedText.first == Action.TodoListComplete) {
                Action.TodoListNotComplete
            } else {
                Action.TodoListComplete
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
}

@Composable
fun rememberTextEditorValue(
    initialMarkdown: String
): TextEditorValue {
    return remember {
        TextEditorValue(initialMarkdown)
    }
}
