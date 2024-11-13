package com.haghpanh.pienote.commonui.texteditor.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.utils.TextEditorAction
import com.haghpanah.pienote.utils.TextEditorValue
import com.haghpanah.pienote.utils.getNameStringId
import org.jetbrains.compose.resources.stringResource

@Composable
fun TextEditorActionBar(
    textEditorValue: TextEditorValue,
    textEditorFocusedItemIndex: Int?,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    shape: Shape = MaterialTheme.shapes.medium,
    onAddSection: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clip(shape)
            .background(backgroundColor)
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(
            items = TextEditorAction.entries.filter {
                it !in setOf(
                    TextEditorAction.TodoListComplete,
                    TextEditorAction.OrderedList
                )
            }
        ) { action ->
            Box(modifier = Modifier.fillMaxWidth()) {
                TextButton(
                    onClick = {
                        textEditorValue.addSection(
                            action = action,
                            index = textEditorFocusedItemIndex.takeIf { it != null }
                        )
                        onAddSection()
                    }
                ) {
                    action.getNameStringId()?.let {
                        Text(text = stringResource(it))
                    }
                }
            }
        }
    }
}
