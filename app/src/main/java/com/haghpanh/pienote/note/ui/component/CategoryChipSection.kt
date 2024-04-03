package com.haghpanh.pienote.note.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.baseui.theme.PienoteTheme
import com.haghpanh.pienote.note.ui.Category

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChipSection(
    category: Category?,
    isEditing: Boolean
) {
    val chipContentColor = PienoteTheme.colors.onBackground.copy(alpha = 0.7f)

    category?.let { cat ->
        AnimatedContent(
            targetState = isEditing,
            label = "select category chip"
        ) {
            if (it) {
                Chip(
                    modifier = Modifier.padding(start = 14.dp),
                    onClick = {
                        //TODO show categories
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = PienoteTheme.colors.background,
                        contentColor = chipContentColor
                    ),
                    border = BorderStroke(1.dp, chipContentColor)
                ) {
                    Text(
                        text = "Change Category",
                        style = PienoteTheme.typography.subtitle2
                    )
                }
            } else {
                Chip(
                    modifier = Modifier.padding(start = 14.dp),
                    onClick = {
                        //TODO navigate to category
                    },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = PienoteTheme.colors.background,
                        contentColor = chipContentColor
                    ),
                    border = BorderStroke(1.dp, chipContentColor)
                ) {
                    Text(
                        text = cat.name,
                        style = PienoteTheme.typography.subtitle2
                    )
                }
            }
        }
    }

    AnimatedVisibility(visible = category == null && isEditing) {
        Chip(
            modifier = Modifier.padding(start = 14.dp),
            onClick = {
                //TODO navigate to category
            },
            colors = ChipDefaults.chipColors(
                backgroundColor = PienoteTheme.colors.background,
                contentColor = chipContentColor
            ),
            border = BorderStroke(1.dp, chipContentColor)
        ) {
            Text(
                text = "Select Category",
                style = PienoteTheme.typography.subtitle2
            )
        }
    }
}