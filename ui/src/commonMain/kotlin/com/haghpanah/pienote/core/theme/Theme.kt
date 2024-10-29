package com.haghpanah.pienote.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.material3.Typography
import com.haghpanah.pienote.theme.LocalPienoteShapes
import com.haghpanah.pienote.theme.PienoteShapes

@Composable
fun PienoteTheme(
    typography: Typography = PienoteTheme.typography,
    colors: ColorScheme = PienoteTheme.colors,
    pienoteShapes: PienoteShapes = PienoteTheme.shapes,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = typography,
        colorScheme = colors,
        content = content,
        shapes = pienoteShapes.toShapes()
    )
}

object PienoteTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme()) {
            LocalDarkColors.current
        } else {
            LocalLightColors.current
        }

    val typography: Typography
        @Composable
        get() = pienoteTypography

    val icon: PienoteIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val shapes: PienoteShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalPienoteShapes.current
}
