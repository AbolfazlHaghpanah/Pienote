package com.haghpanah.pienote.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.usecase.common.ObserveThemeUseCase
import org.koin.java.KoinJavaComponent.inject

@Composable
fun PienoteTheme(
    typography: Typography = PienoteTheme.typography,
    colors: ColorScheme = PienoteTheme.colors,
    pienoteShapes: PienoteShapes = PienoteTheme.shapes,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        typography = typography,
        colorScheme = colors,
        content = content,
        shapes = pienoteShapes.toShapes()
    )
}

object PienoteTheme {
    val isDarkMode: Boolean
        @Composable
        get() {
            val observeThemeUseCase: ObserveThemeUseCase by inject(ObserveThemeUseCase::class.java)
            val currentTheme by observeThemeUseCase()
                .collectAsState(initial = ThemeType.SystemDefault)
            return when (currentTheme) {
                ThemeType.Dark -> true
                ThemeType.Light -> false
                else -> isSystemInDarkTheme()
            }
        }

    val colors: ColorScheme
        @Composable
        get() = if (isDarkMode) {
            LocalDarkColors.current
        } else {
            LocalLightColors.current
        }

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
