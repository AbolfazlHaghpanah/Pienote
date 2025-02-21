package com.haghpanah.pienote.ui.configurations

import androidx.compose.runtime.Composable
import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType

@Composable
internal actual fun ConfigurationsScreen(
    state: ConfigurationsViewState,
    onBack: () -> Unit,
    onSetTheme: (ThemeType) -> Unit,
    onSetAppLanguage: (SupportedLanguage) -> Unit,
) {
}