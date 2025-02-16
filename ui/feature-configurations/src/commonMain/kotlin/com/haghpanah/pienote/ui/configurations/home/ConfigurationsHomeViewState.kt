package com.haghpanah.pienote.ui.configurations.home

import com.haghpanah.pienote.model.ThemeType

data class ConfigurationsHomeViewState(
    val currentTheme: ThemeType? = null,
) {
    val isLoading = currentTheme == null
}
