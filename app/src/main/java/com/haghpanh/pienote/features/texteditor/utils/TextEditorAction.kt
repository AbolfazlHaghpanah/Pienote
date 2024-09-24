package com.haghpanh.pienote.features.texteditor.utils

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haghpanh.pienote.R

private val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))
private val robotoBoldFont = FontFamily(Font(R.font.roboto_bold, FontWeight.Normal))

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
        TextEditorAction.H1 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 38.sp
        )

        TextEditorAction.H2 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 32.sp
        )

        TextEditorAction.H3 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 28.sp
        )

        TextEditorAction.H4 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 24.sp
        )

        TextEditorAction.TodoListNotComplete -> TextStyle.Default.copy(
            fontFamily = robotoRegularFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.None,
        )

        TextEditorAction.TodoListComplete -> TextStyle.Default.copy(
            fontFamily = robotoRegularFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.LineThrough
        )

        TextEditorAction.List -> TextStyle.Default.copy(
            fontFamily = robotoRegularFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )

        TextEditorAction.OrderedList -> TextStyle.Default.copy(
            fontFamily = robotoRegularFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )

        TextEditorAction.Non -> TextStyle.Default.copy(
            fontFamily = robotoRegularFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}

fun TextEditorAction.getNameStringId(): Int? {
    return when (this) {
        TextEditorAction.H1 -> R.string.label_h1
        TextEditorAction.H2 -> R.string.label_h2
        TextEditorAction.H3 -> R.string.label_h3
        TextEditorAction.H4 -> R.string.label_h4
        TextEditorAction.List -> R.string.label_list
        TextEditorAction.TodoListNotComplete -> R.string.label_to_do
        TextEditorAction.OrderedList -> R.string.label_ordered_list
        else -> null
    }
}

fun TextEditorAction.getPlaceHolderStringId(): Int? {
    return when (this) {
        TextEditorAction.Non -> R.string.label_write_three_dot
        TextEditorAction.H1 -> R.string.label_header1
        TextEditorAction.H2 -> R.string.label_header2
        TextEditorAction.H3 -> R.string.label_header3
        TextEditorAction.H4 -> R.string.label_header4
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

        else -> null
    }
}
