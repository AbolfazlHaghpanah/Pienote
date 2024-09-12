package com.haghpanh.pienote.features.note.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haghpanh.pienote.R
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun PienoteTextEditorBar(
    selectedText: String,
    onAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 48.dp)
            .padding(bottom = 8.dp)
            .background(PienoteTheme.colors.surface, PienoteTheme.shapes.rounded)
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextEditorBarOptions.entries.forEach {
            Box(
                Modifier
                    .padding(horizontal = 16.dp)
                    .clickable {
                        onAction(
                            it.action(selectedText)
                        )
                    }
                    .padding(4.dp)
            ) {
                Text(
                    text = it.symbol,
                    style = PienoteTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun PienoteTextEditorBarPreview(modifier: Modifier = Modifier) {
    PienoteTheme {
        PienoteTextEditorBar(
            selectedText = "",
            onAction = {}
        )
    }
}

enum class TextEditorBarOptions(
    val symbol: AnnotatedString,
    val action: (String) -> String
) {
    PLUSES(
        symbol = AnnotatedString(
            text = "+",
            spanStyle = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold))
            )
        ),
        action = { "" }
    ),

    BOLD(
        symbol = AnnotatedString(
            text = "B",
            spanStyle = SpanStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold))
            )
        ),
        action = {
            if (it.startsWith("**") && it.endsWith("**")) {
                it.removePrefix("**").removeSuffix("**")
            } else {
                "**$it**"
            }
        }
    ),

    CODE(
        symbol = AnnotatedString(
            text = "</>",
            spanStyle = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold))
            )
        ),
        action = {
            if (it.startsWith("`") && it.endsWith("`")) {
                it.removePrefix("`").removeSuffix("`")
            } else {
                "`$it`"
            } }
    ),

    UNDERLINE(
        symbol = AnnotatedString(
            text = "U",
            spanStyle = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textDecoration = TextDecoration.Underline,
                fontFamily = FontFamily(Font(R.font.roboto_bold))
            )
        ),
        action = { "_+$it+_" }
    )
}