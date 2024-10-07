package com.haghpanh.pienote.commonui.texteditor.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue

/**
 * A utility class that manages the state of a text editor with markdown rendering support.
 *
 * @param initialMarkdown The initial markdown text to be parsed and rendered into the text editor.
 *
 * The `TextEditorValue` class holds the markdown content and the corresponding rendered annotated string.
 * It also manages a list of `RenderedText` sections, each of which can have a specific action associated with it,
 * such as headers, or to-do list and other actions. It provides methods to modify the sections and to synchronize
 * the markdown content with the rendered text and vice versa.
 */
class TextEditorValue(
    initialMarkdown: String = "",
) {
    /**
     * The current markdown text representing the content of the text editor.
     * It's updated by syncing with the list of `RenderedText` objects.
     */
    var markdown by mutableStateOf(initialMarkdown)
        private set

    /**
     * The rendered annotated string built from the markdown text, containing text and styles.
     * It's updated by syncing with the list of `RenderedText` objects.
     */
    var annotatedString by mutableStateOf(renderMarkdownToAnnotatedString(markdown))
        private set

    /**
     * A list of `RenderedText` objects, each representing a section of text with an
     * associated action (e.g., bold, italic).
     * Initialized by splitting the initial markdown into separate sections.
     */
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

    /**
     * Returns the list of `RenderedText` objects, each containing an action and text field value.
     * This function ensures immutability of the list by returning a copy of the objects.
     *
     * @return List of `RenderedText` objects.
     */
    fun getRenderedTexts(): List<RenderedText> =
        renderedTexts.map { RenderedText(it.action, it.value) }

    /**
     * Adds a new section to the editor with the specified action at a given index.
     * If no index is provided, the section is appended at the end.
     *
     * @param action The action to be associated with the new section (e.g., bold, italic).
     * @param index Optional index where the new section should be inserted. If null, section is added at the end.
     */
    fun addSection(action: TextEditorAction, index: Int? = null) {
        if (index != null) {
            renderedTexts.add(index + 1, RenderedText(action, TextFieldValue()))
        } else {
            renderedTexts.add(RenderedText(action, TextFieldValue()))
        }
    }

    /**
     * Removes a section from the editor at the specified index.
     *
     * @param index The index of the section to be removed.
     */
    fun removeSection(index: Int) {
        renderedTexts.removeAt(index)
    }

    /**
     * Updates the action associated with a section at the specified index.
     *
     * @param index The index of the section to update.
     * @param newAction The new action to associate with the section.
     */
    fun updateAction(index: Int, newAction: TextEditorAction) {
        renderedTexts[index] = renderedTexts[index].copy(action = newAction)
    }

    /**
     * Updates the text value of a section at the specified index.
     *
     * @param index The index of the section to update.
     * @param value The new `TextFieldValue` to update the section with.
     */
    fun onEachValueChange(index: Int, value: TextFieldValue) {
        renderedTexts[index] = renderedTexts[index].copy(value = value)
    }

    /**
     * Toggles the completion state of a TO-DO list item at the specified index.
     * Only applies if the action is related to TO-DO items (e.g., `TodoListComplete`, `TodoListNotComplete`).
     *
     * @param index The index of the TO-DO item to toggle.
     */
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

    /**
     * Updates the markdown content with the provided value.
     * This triggers synchronization of the rendered texts and annotated string.
     *
     * @param value The new markdown value to set.
     */
    fun updateMarkdown(value: String) {
        markdown = value
        syncRenderedTextsWithMarkdown()
        syncAnnotatedStringWithRenderedTexts()
    }

    /**
     * Ensures that the markdown and annotated string are synchronized with the current state.
     * Should be called when retrieving the final state of the `TextEditorValue`.
     *
     * @return The current `TextEditorValue` after ensuring synchronization.
     */
    fun await(): TextEditorValue {
        syncAnnotatedStringWithRenderedTexts()
        syncMarkdownWithRenderedText()
        return this
    }

    /**
     * Synchronizes the markdown text with the list of rendered texts, ensuring that
     * each section in `renderedTexts` matches the markdown content.
     */
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

    /**
     * Synchronizes the annotated string with the current rendered texts, applying the
     * appropriate styles (e.g., bold, italic) to the sections.
     */
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

    /**
     * Synchronizes the markdown text with the current rendered texts.
     * This is done by concatenating each section's text and its action.
     */
    private fun syncMarkdownWithRenderedText() {
        emptyMarkdown()
        renderedTexts.forEach {
            markdown += "${it.action?.key}${it.value.text}\n\n"
        }
    }

    /**
     * Clears the markdown content, setting it to an empty string.
     */
    private fun emptyMarkdown() {
        markdown = ""
    }

    companion object {
        val saver = Saver<TextEditorValue, String>(
            save = { value ->
                value.await().markdown
            },
            restore = { markdown ->
                TextEditorValue(markdown)
            }
        )
    }
}

/**
 * Data class representing a rendered text section in the editor.
 *
 * @param action The action associated with the section (e.g., list, header, TO-DO list).
 * @param value The text field value for the section.
 */
data class RenderedText(
    val action: TextEditorAction?,
    val value: TextFieldValue
)

/**
 * A Composable function that remembers a `TextEditorValue` with the initial markdown content.
 * The state is preserved across recompositions using the `rememberSaveable` function.
 *
 * @param initialMarkdown The initial markdown content to initialize the `TextEditorValue`.
 * @return The remembered `TextEditorValue`.
 */
@Composable
fun rememberTextEditorValue(
    initialMarkdown: String
): TextEditorValue {
    return rememberSaveable(saver = TextEditorValue.saver) {
        TextEditorValue(initialMarkdown)
    }
}
