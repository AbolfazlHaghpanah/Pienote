package com.haghpanah.pienote.coreui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.haghpanh.pienote.commonui.theme.backgroundDark
import com.haghpanh.pienote.commonui.theme.backgroundLight
import com.haghpanh.pienote.commonui.theme.errorContainerDark
import com.haghpanh.pienote.commonui.theme.errorContainerLight
import com.haghpanh.pienote.commonui.theme.errorDark
import com.haghpanh.pienote.commonui.theme.errorLight
import com.haghpanh.pienote.commonui.theme.inverseOnSurfaceDark
import com.haghpanh.pienote.commonui.theme.inverseOnSurfaceLight
import com.haghpanh.pienote.commonui.theme.inversePrimaryDark
import com.haghpanh.pienote.commonui.theme.inversePrimaryLight
import com.haghpanh.pienote.commonui.theme.inverseSurfaceDark
import com.haghpanh.pienote.commonui.theme.inverseSurfaceLight
import com.haghpanh.pienote.commonui.theme.onBackgroundDark
import com.haghpanh.pienote.commonui.theme.onBackgroundLight
import com.haghpanh.pienote.commonui.theme.onErrorContainerDark
import com.haghpanh.pienote.commonui.theme.onErrorContainerLight
import com.haghpanh.pienote.commonui.theme.onErrorDark
import com.haghpanh.pienote.commonui.theme.onErrorLight
import com.haghpanh.pienote.commonui.theme.onPrimaryContainerDark
import com.haghpanh.pienote.commonui.theme.onPrimaryContainerLight
import com.haghpanh.pienote.commonui.theme.onPrimaryDark
import com.haghpanh.pienote.commonui.theme.onPrimaryLight
import com.haghpanh.pienote.commonui.theme.onSecondaryContainerDark
import com.haghpanh.pienote.commonui.theme.onSecondaryContainerLight
import com.haghpanh.pienote.commonui.theme.onSecondaryDark
import com.haghpanh.pienote.commonui.theme.onSecondaryLight
import com.haghpanh.pienote.commonui.theme.onSurfaceDark
import com.haghpanh.pienote.commonui.theme.onSurfaceLight
import com.haghpanh.pienote.commonui.theme.onSurfaceVariantDark
import com.haghpanh.pienote.commonui.theme.onSurfaceVariantLight
import com.haghpanh.pienote.commonui.theme.onTertiaryContainerDark
import com.haghpanh.pienote.commonui.theme.onTertiaryContainerLight
import com.haghpanh.pienote.commonui.theme.onTertiaryDark
import com.haghpanh.pienote.commonui.theme.onTertiaryLight
import com.haghpanh.pienote.commonui.theme.outlineDark
import com.haghpanh.pienote.commonui.theme.outlineLight
import com.haghpanh.pienote.commonui.theme.outlineVariantDark
import com.haghpanh.pienote.commonui.theme.outlineVariantLight
import com.haghpanh.pienote.commonui.theme.primaryContainerDark
import com.haghpanh.pienote.commonui.theme.primaryContainerLight
import com.haghpanh.pienote.commonui.theme.primaryDark
import com.haghpanh.pienote.commonui.theme.primaryLight
import com.haghpanh.pienote.commonui.theme.scrimDark
import com.haghpanh.pienote.commonui.theme.scrimLight
import com.haghpanh.pienote.commonui.theme.secondaryContainerDark
import com.haghpanh.pienote.commonui.theme.secondaryContainerLight
import com.haghpanh.pienote.commonui.theme.secondaryDark
import com.haghpanh.pienote.commonui.theme.secondaryLight
import com.haghpanh.pienote.commonui.theme.surfaceBrightDark
import com.haghpanh.pienote.commonui.theme.surfaceBrightLight
import com.haghpanh.pienote.commonui.theme.surfaceContainerDark
import com.haghpanh.pienote.commonui.theme.surfaceContainerHighDark
import com.haghpanh.pienote.commonui.theme.surfaceContainerHighLight
import com.haghpanh.pienote.commonui.theme.surfaceContainerHighestDark
import com.haghpanh.pienote.commonui.theme.surfaceContainerHighestLight
import com.haghpanh.pienote.commonui.theme.surfaceContainerLight
import com.haghpanh.pienote.commonui.theme.surfaceContainerLowDark
import com.haghpanh.pienote.commonui.theme.surfaceContainerLowLight
import com.haghpanh.pienote.commonui.theme.surfaceContainerLowestDark
import com.haghpanh.pienote.commonui.theme.surfaceContainerLowestLight
import com.haghpanh.pienote.commonui.theme.surfaceDark
import com.haghpanh.pienote.commonui.theme.surfaceDimDark
import com.haghpanh.pienote.commonui.theme.surfaceDimLight
import com.haghpanh.pienote.commonui.theme.surfaceLight
import com.haghpanh.pienote.commonui.theme.surfaceVariantDark
import com.haghpanh.pienote.commonui.theme.surfaceVariantLight
import com.haghpanh.pienote.commonui.theme.tertiaryContainerDark
import com.haghpanh.pienote.commonui.theme.tertiaryContainerLight
import com.haghpanh.pienote.commonui.theme.tertiaryDark
import com.haghpanh.pienote.commonui.theme.tertiaryLight

fun darkColorScheme() = ColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    inversePrimary = inversePrimaryDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    surfaceTint = Color.Unspecified,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    surfaceBright = surfaceBrightDark,
    surfaceDim = surfaceDimDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainerLowest = surfaceContainerLowestDark
)

fun lightColorScheme() = ColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    inversePrimary = inversePrimaryLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    surfaceTint = Color.Unspecified,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    surfaceBright = surfaceBrightLight,
    surfaceDim = surfaceDimLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainerLowest = surfaceContainerLowestLight
)

val LocalDarkColors = staticCompositionLocalOf { darkColorScheme() }
val LocalLightColors = staticCompositionLocalOf { lightColorScheme() }
