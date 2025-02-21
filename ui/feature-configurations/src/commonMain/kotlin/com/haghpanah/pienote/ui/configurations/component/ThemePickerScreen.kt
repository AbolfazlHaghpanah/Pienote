package com.haghpanah.pienote.ui.configurations.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.haghpanah.pienote.designsystem.theme.PienoteTheme
import com.haghpanah.pienote.designsystem.theme.darkColorScheme
import com.haghpanah.pienote.designsystem.theme.lightColorScheme
import com.haghpanah.pienote.model.ThemeType
import org.jetbrains.compose.resources.stringResource
import pienote.ui.base.generated.resources.Res
import pienote.ui.base.generated.resources.label_dark
import pienote.ui.base.generated.resources.label_light
import pienote.ui.base.generated.resources.label_system_default
import pienote.ui.base.generated.resources.label_theme


@Composable
internal fun ThemePickerSection(
    onSelectTheme: (ThemeType) -> Unit,
    selectedTheme: ThemeType,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(Res.string.label_theme),
            style = PienoteTheme.typography.bodyLarge,
            color = PienoteTheme.colors.onBackground
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            ThemeType.entries.forEach { themeType ->
                val animatedWeight by animateFloatAsState(
                    if (themeType == selectedTheme) {
                        1f
                    } else {
                        0.5f
                    }
                )

                Box(
                    Modifier
                        .clip(PienoteTheme.shapes.medium)
                        .clickable { onSelectTheme(themeType) }
                        .background(
                            brush = themeType.getPickerItemColor(),
                            shape = PienoteTheme.shapes.medium
                        )
                        .height(148.dp)
                        .weight(animatedWeight)
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = themeType.getShowText(),
                        style = PienoteTheme.typography.titleMedium,
                        color = themeType.getPickerTextItemColor(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemeType.getShowText(): String =
    when (this) {
        ThemeType.Dark -> stringResource(Res.string.label_dark)
        ThemeType.Light -> stringResource(Res.string.label_light)
        ThemeType.SystemDefault -> stringResource(Res.string.label_system_default)
    }

@Composable
private fun ThemeType.getPickerItemColor(): Brush =
    when (this) {
        ThemeType.Dark -> Brush.radialGradient(
            colors = listOf(
                darkColorScheme().primary,
                darkColorScheme().surface,
            ),
            center = Offset(200f, 0f),
            radius = 200f,
        )

        ThemeType.Light -> Brush.radialGradient(
            listOf(
                lightColorScheme().primary,
                lightColorScheme().surface,
            ),
            center = Offset.Zero,
            radius = 200f,
        )

        ThemeType.SystemDefault -> Brush.linearGradient(
            listOf(
                lightColorScheme().surface,
                PienoteTheme.colors.primary,
                darkColorScheme().surface,
            )
        )
    }

@Composable
private fun ThemeType.getPickerTextItemColor(): Color =
    when (this) {
        ThemeType.Dark -> darkColorScheme().onBackground
        ThemeType.Light -> lightColorScheme().onBackground
        ThemeType.SystemDefault -> PienoteTheme.colors.onPrimaryContainer
    }