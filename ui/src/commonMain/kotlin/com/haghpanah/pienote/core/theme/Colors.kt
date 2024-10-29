package com.haghpanah.pienote.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.haghpanah.pienote.theme.backgroundDark
import com.haghpanah.pienote.theme.backgroundLight
import com.haghpanah.pienote.theme.errorContainerDark
import com.haghpanah.pienote.theme.errorContainerLight
import com.haghpanah.pienote.theme.errorDark
import com.haghpanah.pienote.theme.errorLight
import com.haghpanah.pienote.theme.inverseOnSurfaceDark
import com.haghpanah.pienote.theme.inverseOnSurfaceLight
import com.haghpanah.pienote.theme.inversePrimaryDark
import com.haghpanah.pienote.theme.inversePrimaryLight
import com.haghpanah.pienote.theme.inverseSurfaceDark
import com.haghpanah.pienote.theme.inverseSurfaceLight
import com.haghpanah.pienote.theme.onBackgroundDark
import com.haghpanah.pienote.theme.onBackgroundLight
import com.haghpanah.pienote.theme.onErrorContainerDark
import com.haghpanah.pienote.theme.onErrorContainerLight
import com.haghpanah.pienote.theme.onErrorDark
import com.haghpanah.pienote.theme.onErrorLight
import com.haghpanah.pienote.theme.onPrimaryContainerDark
import com.haghpanah.pienote.theme.onPrimaryContainerLight
import com.haghpanah.pienote.theme.onPrimaryDark
import com.haghpanah.pienote.theme.onPrimaryLight
import com.haghpanah.pienote.theme.onSecondaryContainerDark
import com.haghpanah.pienote.theme.onSecondaryContainerLight
import com.haghpanah.pienote.theme.onSecondaryDark
import com.haghpanah.pienote.theme.onSecondaryLight
import com.haghpanah.pienote.theme.onSurfaceDark
import com.haghpanah.pienote.theme.onSurfaceLight
import com.haghpanah.pienote.theme.onSurfaceVariantDark
import com.haghpanah.pienote.theme.onSurfaceVariantLight
import com.haghpanah.pienote.theme.onTertiaryContainerDark
import com.haghpanah.pienote.theme.onTertiaryContainerLight
import com.haghpanah.pienote.theme.onTertiaryDark
import com.haghpanah.pienote.theme.onTertiaryLight
import com.haghpanah.pienote.theme.outlineDark
import com.haghpanah.pienote.theme.outlineLight
import com.haghpanah.pienote.theme.outlineVariantDark
import com.haghpanah.pienote.theme.outlineVariantLight
import com.haghpanah.pienote.theme.primaryContainerDark
import com.haghpanah.pienote.theme.primaryContainerLight
import com.haghpanah.pienote.theme.primaryDark
import com.haghpanah.pienote.theme.primaryLight
import com.haghpanah.pienote.theme.scrimDark
import com.haghpanah.pienote.theme.scrimLight
import com.haghpanah.pienote.theme.secondaryContainerDark
import com.haghpanah.pienote.theme.secondaryContainerLight
import com.haghpanah.pienote.theme.secondaryDark
import com.haghpanah.pienote.theme.secondaryLight
import com.haghpanah.pienote.theme.surfaceBrightDark
import com.haghpanah.pienote.theme.surfaceBrightLight
import com.haghpanah.pienote.theme.surfaceContainerDark
import com.haghpanah.pienote.theme.surfaceContainerHighDark
import com.haghpanah.pienote.theme.surfaceContainerHighLight
import com.haghpanah.pienote.theme.surfaceContainerHighestDark
import com.haghpanah.pienote.theme.surfaceContainerHighestLight
import com.haghpanah.pienote.theme.surfaceContainerLight
import com.haghpanah.pienote.theme.surfaceContainerLowDark
import com.haghpanah.pienote.theme.surfaceContainerLowLight
import com.haghpanah.pienote.theme.surfaceContainerLowestDark
import com.haghpanah.pienote.theme.surfaceContainerLowestLight
import com.haghpanah.pienote.theme.surfaceDark
import com.haghpanah.pienote.theme.surfaceDimDark
import com.haghpanah.pienote.theme.surfaceDimLight
import com.haghpanah.pienote.theme.surfaceLight
import com.haghpanah.pienote.theme.surfaceVariantDark
import com.haghpanah.pienote.theme.surfaceVariantLight
import com.haghpanah.pienote.theme.tertiaryContainerDark
import com.haghpanah.pienote.theme.tertiaryContainerLight
import com.haghpanah.pienote.theme.tertiaryDark
import com.haghpanah.pienote.theme.tertiaryLight

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
