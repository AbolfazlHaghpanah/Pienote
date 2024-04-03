package com.haghpanh.pienote.note.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.haghpanh.pienote.R
import com.haghpanh.pienote.baseui.theme.PienoteTheme
import com.haghpanh.pienote.note.ui.Category

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryChipSection(
    category: Category?,
    isEditing: Boolean
) {
    val chipContentColor by animateColorAsState(
        targetValue = if (isEditing) {
            PienoteTheme.colors.onBackground
        } else {
            PienoteTheme.colors.onBackground.copy(alpha = 0.7f)
        },
        label = "category chip color"
    )

    category?.let { cat ->
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
                text = stringResource(R.string.label_select_category),
                style = PienoteTheme.typography.subtitle2
            )
        }
    }
}