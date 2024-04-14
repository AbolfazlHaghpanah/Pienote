package com.haghpanh.pienote.feature_note.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
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
import com.haghpanh.pienote.common_ui.theme.PienoteTheme
import com.haghpanh.pienote.feature_note.ui.Category

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChipSection(
    modifier: Modifier = Modifier,
    category: Category?,
    isEditing: Boolean,
    categories: List<Category>,
    onCategorySelect: (Int?) -> Unit
) {
    var isWantToSelect by remember { mutableStateOf(false) }
    val chipContentColor by animateColorAsState(
        targetValue = if (isEditing) {
            if (isWantToSelect) {
                PienoteTheme.colors.error.copy(alpha = 0.7f)
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
    val onChipSelect = {
        if (isEditing) isWantToSelect = !isWantToSelect
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
            Chip(
                modifier = Modifier.padding(start = 30.dp),
                onClick = onChipSelect,
                colors = ChipDefaults.chipColors(
                    backgroundColor = PienoteTheme.colors.background,
                    contentColor = chipContentColor
                ),
                border = BorderStroke(1.dp, chipContentColor)
            ) {
                Text(
                    text = if (isWantToSelect) chipText else cat.name,
                    style = PienoteTheme.typography.subtitle2
                )
            }
        }

        AnimatedVisibility(visible = category == null && isEditing) {
            Chip(
                modifier = Modifier.padding(start = 30.dp),
                onClick = onChipSelect,
                colors = ChipDefaults.chipColors(
                    backgroundColor = PienoteTheme.colors.background,
                    contentColor = chipContentColor
                ),
                border = BorderStroke(1.dp, chipContentColor)
            ) {
                Text(
                    text = chipText,
                    style = PienoteTheme.typography.subtitle2
                )
            }
        }

        AnimatedVisibility(visible = isWantToSelect) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                categories.forEach { cat ->
                    Chip(
                        onClick = {
                            onChipSelect()
                            onCategorySelect(cat.id)
                        },
                        colors = ChipDefaults.chipColors(
                            backgroundColor = PienoteTheme.colors.background,
                            contentColor = PienoteTheme.colors.onBackground
                        ),
                        border = BorderStroke(1.dp, PienoteTheme.colors.onBackground)
                    ) {
                        Text(
                            text = cat.name,
                            style = PienoteTheme.typography.subtitle2
                        )
                    }
                }
            }
        }
    }
}