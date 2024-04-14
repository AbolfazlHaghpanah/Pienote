package com.haghpanh.pienote.common_ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

class Colors(
    primary: Color,
    onPrimary: Color,
    secondary: Color,
    onSecondary: Color,
    background: Color,
    onBackground: Color,
    surface: Color,
    onSurface: Color,
    error: Color,
    onError: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var background by mutableStateOf(background)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var surface by mutableStateOf(surface)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var error by mutableStateOf(error)
        private set
    var onError by mutableStateOf(onError)
        private set
    var isLight by mutableStateOf(isLight)
        private set

    fun toColor() = Colors(
        primary = primary,
        secondary = secondary,
        background = background,
        surface = surface,
        error = error,
        isLight = isLight,
        onBackground = onBackground,
        onError = error,
        primaryVariant = primary,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        onSurface = onSurface,
        secondaryVariant = secondary
    )
}

fun darkColors() = Colors(
    primary = PrimaryDark,
    secondary = SecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    error = ErrorDark,
    onBackground = OnBackgroundDark,
    onError = OnErrorDark,
    onPrimary = OnPrimaryDark,
    onSecondary = OnSecondaryDark,
    onSurface = OnSurfaceDark,
    isLight = false
)

fun lightColors() = Colors(
    primary = PrimaryLight,
    secondary = SecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    error = ErrorLight,
    onBackground = OnBackgroundLight,
    onError = OnErrorLight,
    onPrimary = OnPrimaryLight,
    onSecondary = OnSecondaryLight,
    onSurface = OnSurfaceLight,
    isLight = true
)

val LocalDarkColors = staticCompositionLocalOf { darkColors() }
val LocalLightColors = staticCompositionLocalOf { lightColors() }
