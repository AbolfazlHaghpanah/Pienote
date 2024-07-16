package com.haghpanh.pienote.features.note.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.commonui.theme.PienoteTheme
import com.haghpanh.pienote.features.note.ui.Category

@Composable
fun CategoryChipSection(
    modifier: Modifier = Modifier,
    category: Category?,
    isEditing: Boolean,
    categories: List<Category>,
    onCategorySelect: (Int?) -> Unit,
    onClickCategory: (Int) -> Unit
) {
    var isWantToSelect by remember { mutableStateOf(false) }
    val chipContentColor by animateColorAsState(
        targetValue = if (isEditing) {
            if (isWantToSelect) {
                PienoteTheme.colors.error
            } else if (category == null) {
                PienoteTheme.colors.tertiaryContainer
            } else {
                PienoteTheme.colors.onBackground
            }
        } else {
            PienoteTheme.colors.onBackground.copy(alpha = 0.7f)
        },
        label = "category chip color"
    )
    val chipText by remember {
        derivedStateOf {
            if (!isWantToSelect) "Select Category" else "Cancel"
        }
    }
    val onChipSelect: (Int?) -> Unit = {
        if (isEditing) {
            isWantToSelect = !isWantToSelect
        } else {
            onClickCategory(it ?: -1)
        }
    }

    LaunchedEffect(isEditing) {
        if (!isEditing) {
            isWantToSelect = false
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        category?.let { cat ->
            SuggestionChip(
                modifier = Modifier.padding(start = 30.dp),
                onClick = { onChipSelect(cat.id) },
                label = {
                    Text(
                        text = if (isWantToSelect) chipText else cat.name,
                        style = PienoteTheme.typography.labelSmall,
                    )
                },
                border = BorderStroke(1.dp, chipContentColor),
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = chipContentColor
                ),
                shape = PienoteTheme.shapes.rounded
            )
        }

        AnimatedVisibility(visible = category == null && isEditing) {
            SuggestionChip(
                modifier = Modifier.padding(start = 30.dp),
                onClick = { onChipSelect(null) },
                border = BorderStroke(1.dp, chipContentColor),
                label = {
                    Text(
                        text = chipText,
                        style = PienoteTheme.typography.labelMedium
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = chipContentColor
                ),
                shape = PienoteTheme.shapes.rounded
            )
        }

        AnimatedVisibility(visible = isWantToSelect) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                categories.forEach { cat ->
                    SuggestionChip(
                        onClick = {
                            onChipSelect(cat.id)
                            onCategorySelect(cat.id)
                        },
                        border = BorderStroke(1.dp, PienoteTheme.colors.onBackground),
                        label = {
                            Text(
                                text = cat.name,
                                style = PienoteTheme.typography.labelMedium
                            )
                        },
                        shape = PienoteTheme.shapes.rounded
                    )
                }
            }
        }
    }
}
