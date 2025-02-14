package com.haghpanah.pienote.note.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.designsystem.component.PienoteColorPicker
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.label_change_color
import pienote.ui.base.generated.resources.label_done

@Composable
fun NoteColorSection(
    selectedColor: String?,
    onUpdateColor: (String?) -> Unit,
    color: Color,
    isEditing: Boolean
) {
    AnimatedVisibility(
        isEditing,
        label = "shows choose color with color picker"
    ) {
        var shouldShowColorPicker by remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .background(color)
                    .clickable { shouldShowColorPicker = !shouldShowColorPicker }
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(targetState = shouldShowColorPicker) {
                    if (it) {
                        Text(
                            text = stringResource(Res.string.label_done),
                            color = PienoteTheme.colors.surface,
                            style = PienoteTheme.typography.labelMedium
                        )
                    } else {
                        Text(
                            text = stringResource(Res.string.label_change_color),
                            color = PienoteTheme.colors.surface,
                            style = PienoteTheme.typography.labelMedium
                        )
                    }
                }
            }

            AnimatedVisibility(visible = shouldShowColorPicker) {
                PienoteColorPicker(
                    color = selectedColor,
                    onColorSelection = { onUpdateColor(it) }
                )
            }
        }
    }
}