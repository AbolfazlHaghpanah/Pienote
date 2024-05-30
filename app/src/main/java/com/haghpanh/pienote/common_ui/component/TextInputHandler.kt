package com.haghpanh.pienote.common_ui.component

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.haghpanh.pienote.common_ui.theme.robotoBoldFont

object TextInputHandler {

    fun incBold (value: String) : String =
        "<div class = BOLD>$value</div>"

    fun extractTextAndType(string: String): Pair<String?, String?> {
        val divRegex = Regex("""<div\s+class\s*=\s*"?([^"\s]*)"?>(.*?)</div>""")

        val matchResult = divRegex.find(string)
        val type = matchResult?.groupValues?.getOrNull(1)
        val text = matchResult?.groupValues?.getOrNull(2)

        return Pair(type, text)
    }

    fun createAnnotatedText(value: String): AnnotatedString {
        val matches = extractTextAndType(value)

        return if (!matches.first.isNullOrEmpty() && !matches.second.isNullOrEmpty()) {
            val spanStyle = when (matches.first) {
                "BOLD" -> BOLD_TEXT.toSpanStyle()
                else -> SpanStyle()
            }

            buildAnnotatedString {
                append(matches.second)
                addStyle(spanStyle, start = 0, end = matches.second!!.length)
            }
        } else {
            buildAnnotatedString {
                append(matches.second)
            }
        }
    }

    val BOLD_TEXT = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}