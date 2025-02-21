package com.haghpanah.pienote.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import com.haghpanah.pienote.model.ThemeType

@Composable
fun PienoteTheme(
    isDarkMode: Boolean = true,
    typography: Typography = PienoteTheme.typography,
    colors: ColorScheme = PienoteTheme.colors,
    pienoteShapes: PienoteShapes = PienoteTheme.shapes,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (isDarkMode) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    CompositionLocalProvider(
        LocalColorScheme provides colorScheme
    ) {
        MaterialTheme(
            typography = typography,
            colorScheme = colors,
            shapes = pienoteShapes.toShapes(),
            content = content
        )
    }
}

object PienoteTheme {
    val colors: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColorScheme.current

    val typography: Typography
        @Composable
        get() = pienoteTypography()

    val icon: PienoteIcons
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val shapes: PienoteShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalPienoteShapes.current
}
