package com.haghpanh.pienote.features.textEditor.base

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.haghpanh.pienote.R

private val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))
private val robotoBoldFont = FontFamily(Font(R.font.roboto_bold, FontWeight.Normal))

enum class Action(val key: String) {
    Non(""),
    H1("# "),
    H2("## "),
    H3("### "),
    H4("#### "),
    DotList("- "),
    TodoListNotComplete("- [ ] "),
    TodoListComplete("- [x] "),

    // TODO: find the way
    NumberList(".1 ")
}

fun Action.getTextStyle(): TextStyle {
    return when (this) {
        Action.H1 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 23.sp
        )

        Action.H2 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp
        )

        Action.H3 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 16.sp
        )

        Action.H4 -> TextStyle.Default.copy(
            fontFamily = robotoBoldFont,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp
        )

        Action.TodoListNotComplete -> TextStyle.Default.copy(
            fontFamily = robotoRegularFont,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textDecoration = TextDecoration.None,
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
