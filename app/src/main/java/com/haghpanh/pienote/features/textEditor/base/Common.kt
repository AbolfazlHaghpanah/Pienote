package com.haghpanh.pienote.features.textEditor.base

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString

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

fun String.getActionOrNull(): Action? {
    Action.entries.reversed().forEach { action ->
        if (this.startsWith(action.key)) {
            return action
        }
    }
    return null
}

fun String.removePrefix(): String {
    var newValue = this
    Action.entries.reversed().forEach { action ->
        newValue = newValue.removePrefix(action.key)
    }
    return newValue
}