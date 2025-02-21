package com.haghpanah.pienote.usecase.configurations

import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.repository.ConfigurationsRepository
import com.haghpanah.pienote.utils.PienotePreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetThemeUseCase(
    private val repository: ConfigurationsRepository,
) {
    suspend operator fun invoke(themeType: ThemeType) = withContext(Dispatchers.IO) {
        repository.setTheme(themeType)
    }
}
