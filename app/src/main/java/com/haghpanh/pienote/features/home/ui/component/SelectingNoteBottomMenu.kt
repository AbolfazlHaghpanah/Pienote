package com.haghpanh.pienote.features.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme

@Composable
fun SelectingNoteBottomMenu(
    modifier: Modifier = Modifier,
    onItemClick: (SelectingNoteOptions) -> Unit
) {
    Column(
        modifier = modifier
            .clip(PienoteTheme.shapes.veryLarge)
            .background(PienoteTheme.colors.surfaceContainerHighest)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SelectingNoteOptions
            .entries
            .filter { it.shouldShowInList }
            .chunked(3)
            .forEach { items ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    items.forEach { item ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clip(PienoteTheme.shapes.medium)
                                .clickable { onItemClick(item) },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item.icon?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = item.label,
                                    tint = PienoteTheme.colors.onSurface
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = item.label,
                                style = PienoteTheme.typography.labelLarge,
                                color = PienoteTheme.colors.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
    }
}

enum class SelectingNoteOptions(
    val label: String,
    val shouldShowInList: Boolean,
    val icon: ImageVector? = null,
) {
    MoveToCategory("Move To Category", true, Icons.Rounded.Place),
    AddToFavorite("Add To Favorite", false),
    DeleteNotes("Delete Notes", true, Icons.Rounded.Delete),
    AddCategory("Add Category", true, Icons.Rounded.Add)
}
