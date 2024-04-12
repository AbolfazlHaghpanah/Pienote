package com.haghpanh.pienote.commonui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun PienoteTheme(
    typography: Types = PienoteTheme.typography,
    colors: Colors = PienoteTheme.colors,
    shapes: Shapes = PienoteTheme.shapes,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = typography.toTypography(),
        colors = colors.toColor(),
        content = content,
        shapes = shapes.toShapes()
    )
}

object PienoteTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = if (isSystemInDarkTheme())
            LocalDarkColors.current else LocalLightColors.current

    val typography: Types
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    val icon: Icon
        @Composable
        @ReadOnlyComposable
        get() = LocalIcons.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

}