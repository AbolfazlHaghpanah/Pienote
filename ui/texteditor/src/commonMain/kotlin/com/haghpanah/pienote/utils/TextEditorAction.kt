package com.haghpanah.pienote.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import pienote.ui.texteditor.generated.resources.Res
import pienote.ui.texteditor.generated.resources.label_h1
import pienote.ui.texteditor.generated.resources.label_h2
import pienote.ui.texteditor.generated.resources.label_h3
import pienote.ui.texteditor.generated.resources.label_h4
import pienote.ui.texteditor.generated.resources.label_header1
import pienote.ui.texteditor.generated.resources.label_header2
import pienote.ui.texteditor.generated.resources.label_header3
import pienote.ui.texteditor.generated.resources.label_header4
import pienote.ui.texteditor.generated.resources.label_list
import pienote.ui.texteditor.generated.resources.label_normal_text
import pienote.ui.texteditor.generated.resources.label_ordered_list
import pienote.ui.texteditor.generated.resources.label_t
import pienote.ui.texteditor.generated.resources.label_to_do
import pienote.ui.texteditor.generated.resources.label_write_three_dot

enum class TextEditorAction(val key: String) {
    Non(""),
    H1("# "),
    H2("## "),
    H3("### "),
    H4("#### "),
    List("- "),
    TodoListNotComplete("- [ ] "),
    TodoListComplete("- [x] "),

    // TODO: find the way
    OrderedList(".1 ")
}

fun TextEditorAction.getTextStyle(): TextStyle {
    return when (this) {
        TextEditorAction.H1 -> TextEditorFontProvider.typography.displayMedium
        TextEditorAction.H2 -> TextEditorFontProvider.typography.headlineLarge
        TextEditorAction.H3 -> TextEditorFontProvider.typography.displayMedium
        TextEditorAction.H4 -> TextEditorFontProvider.typography.headlineSmall
        TextEditorAction.TodoListComplete -> TextEditorFontProvider.typography.bodyMedium
        TextEditorAction.TodoListNotComplete -> TextEditorFontProvider.typography.bodyMedium
        TextEditorAction.OrderedList -> TextEditorFontProvider.typography.bodyMedium
        TextEditorAction.List -> TextEditorFontProvider.typography.bodyMedium
        TextEditorAction.Non -> TextEditorFontProvider.typography.bodyMedium
    }
}

fun TextEditorAction.getNameStringId(): StringResource? {
    return when (this) {
        TextEditorAction.Non -> Res.string.label_t
        TextEditorAction.H1 -> Res.string.label_h1
        TextEditorAction.H2 -> Res.string.label_h2
        TextEditorAction.H3 -> Res.string.label_h3
        TextEditorAction.H4 -> Res.string.label_h4
        TextEditorAction.List -> Res.string.label_list
        TextEditorAction.TodoListNotComplete -> Res.string.label_to_do
        TextEditorAction.OrderedList -> Res.string.label_ordered_list
        else -> null
    }
}

fun TextEditorAction.getFullNameStringId(): StringResource? {
    return when (this) {
        TextEditorAction.Non -> Res.string.label_normal_text
        TextEditorAction.H1 -> Res.string.label_header1
        TextEditorAction.H2 -> Res.string.label_header2
        TextEditorAction.H3 -> Res.string.label_header3
        TextEditorAction.H4 -> Res.string.label_header4
        TextEditorAction.List -> Res.string.label_list
        TextEditorAction.TodoListNotComplete -> Res.string.label_to_do
        TextEditorAction.OrderedList -> Res.string.label_ordered_list
        else -> null
    }
}

fun TextEditorAction.getPlaceHolderStringId(): StringResource? {
    return when (this) {
        TextEditorAction.Non -> Res.string.label_write_three_dot
        TextEditorAction.H1 -> Res.string.label_header1
        TextEditorAction.H2 -> Res.string.label_header2
        TextEditorAction.H3 -> Res.string.label_header3
        TextEditorAction.H4 -> Res.string.label_header4
        else -> null
    }
}

@Composable
fun TextEditorAction.CreateIcon(onIconClick: () -> Unit) {
    when (this) {
        TextEditorAction.TodoListComplete, TextEditorAction.TodoListNotComplete -> {
            Checkbox(
                checked = this == TextEditorAction.TodoListComplete,
                onCheckedChange = { onIconClick() }
            )
        }

        TextEditorAction.List -> {
            // TODO change Icon
            Icon(
                modifier = Modifier
                    .padding(14.dp)
                    .size(8.dp),
                imageVector = Icons.Default.Info,
                contentDescription = null
            )
        }

        else -> {}
    }
}
