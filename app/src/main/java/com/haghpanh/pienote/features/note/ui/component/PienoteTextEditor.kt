package com.haghpanh.pienote.features.note.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.haghpanh.pienote.commonui.component.PienoteTextField
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import kotlin.properties.Delegates

@Composable
fun PienoteTextEditor(
    value: TextEditorValue,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        value.getChunkedText().forEachIndexed { index, item ->
            PienoteTextField(
                value = item,
                onValueChange = { value.onValueChange(it, index) },
                textStyle = PienoteTheme.typography.titleMedium
            )
        }

        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(TextEditorValue.Action.entries) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { value.add(it) }) {
                        Text(text = it.name)
                    }
                }
            }
        }
    }
}

class TextEditorValue(
    initialText: String = ""
) {
    private val uiTexts = mutableStateListOf<String>().also { list ->
        if (initialText.isNotEmpty()) {
            initialText.split("\n\n")
                .filter { it.isNotBlank() }
                .forEach {
                    list.add(it)
                }
        }
    }

    private var text: String by Delegates.observable(initialText) { _, oldText, newText ->
        if (newText.isEmpty()) return@observable

        val isRemoved = oldText.length > newText.length

        text.split("\n\n")
            .filter { it.isNotBlank() }
            .forEachIndexed { index, text ->
                if (uiTexts.getOrNull(index) == text) return@forEachIndexed

                if (isRemoved) {
                    uiTexts.removeIf { it == text }
                } else {
                    uiTexts.add(text)
                }
            }
    }

    fun getChunkedText(): List<String> = uiTexts

    fun add(action: Action) {
        text += "\n\n"
        text += "${action.key} "
    }

    fun onValueChange(value: String, index: Int) {
        text = text
            .split("\n\n")
            .mapIndexed { textIndex, s ->
                if (textIndex == index) {
                    value
                } else {
                    s
                }
            }.joinToString { "$it\n\n" }
    }

    enum class Action(val key: String) {
        H1("#"),
        H2("##"),
        H3("###"),
        H4("####"),
        TodoList("- [ ]"),
        DotList("-"),
        NumberList(".1")
    }
}