package com.haghpanah.pienote.ui.configurations

import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.usecase.configurations.ObserveCurrentLanguageUseCase

data class ConfigurationsViewState(
    val currentTheme: ThemeType? = null,
    val currentLanguage: SupportedLanguage? = null,
) {
    val isLoading = currentTheme == null || currentLanguage == null
}
