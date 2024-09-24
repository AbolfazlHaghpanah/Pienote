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

    private val renderedTexts =
        mutableStateListOf<RenderedText>().also { list ->
            if (initialMarkdown.isNotEmpty()) {
                initialMarkdown.split("\n\n")
                    .filter { it.isNotBlank() }
                    .forEach {
                        val text = it.removePrefix()
                        val action = it.getActionOrNull()
                        list.add(RenderedText(action, TextFieldValue(text)))
                    }
            } else {
                list.add(RenderedText(TextEditorAction.Non, TextFieldValue()))
            }
        }

    fun getRenderedTexts(): List<RenderedText> =
        renderedTexts.map { RenderedText(it.action, it.value) }

    fun addSection(action: TextEditorAction, index: Int? = null) {
        if (index != null) {
            renderedTexts.add(index + 1, RenderedText(action, TextFieldValue()))
        } else {
            renderedTexts.add(RenderedText(action, TextFieldValue()))
        }
    }

    fun updateAction(index: Int, newAction: TextEditorAction) {
        renderedTexts[index] = renderedTexts[index].copy(action = newAction)
    }

    fun removeSection(index: Int) {
        renderedTexts.removeAt(index)
    }

    fun onEachValueChange(index: Int, value: TextFieldValue) {
        renderedTexts[index] = renderedTexts[index].copy(value = value)
    }

    fun onCheckTodo(index: Int) {
        val renderedText = renderedTexts[index]

        if (renderedText.action !in setOf(
                TextEditorAction.TodoListComplete,
                TextEditorAction.TodoListNotComplete
            )
        ) {
            return
        }

        val newValue = renderedText.copy(
            action = if (renderedText.action == TextEditorAction.TodoListComplete) {
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
                    renderedTexts.add(RenderedText(action, TextFieldValue(text)))
                }
        }
    }

    private fun syncAnnotatedStringWithRenderedTexts() {
        annotatedString = buildAnnotatedString {
            var count = 0
            renderedTexts.forEach { renderedText ->
                append(renderedText.value.text)

                renderedText.action?.getTextStyle()?.let { textStyle ->
                    addStyle(
                        start = count,
                        end = count + renderedText.value.text.length,
                        style = textStyle.toSpanStyle()
                    )

                    addStyle(
                        start = count,
                        end = count + renderedText.value.text.length,
                        style = textStyle.toParagraphStyle()
                    )
                }

                count += (renderedText.value.text.length)
            }
        }
    }

    private fun syncMarkdownWithRenderedText() {
        emptyMarkdown()
        renderedTexts.forEach {
            markdown += "${it.action?.key}${it.value.text}\n\n"
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

data class RenderedText(
    val action: TextEditorAction?,
    val value: TextFieldValue
)

@Composable
fun rememberTextEditorValue(
    initialMarkdown: String
): TextEditorValue {
    return rememberSaveable(saver = TextEditorValue.saver) {
        TextEditorValue(initialMarkdown)
    }
}
