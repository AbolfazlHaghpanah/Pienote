package pienote.commonui.texteditor.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pienote.commonui.texteditor.utils.TextEditorAction
import pienote.commonui.texteditor.utils.TextEditorValue
import pienote.commonui.texteditor.utils.getNameStringId
import pienote.commonui.theme.PienoteTheme

@Composable
fun TextEditorActionBar(
    textEditorValue: TextEditorValue,
    textEditorFocusedItemIndex: Int?,
    onAddSection: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clip(PienoteTheme.shapes.rounded)
            .background(PienoteTheme.colors.surface)
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
                        Text(text = stringResource(id = it))
                    }
                }
            }
        }
    }
}
