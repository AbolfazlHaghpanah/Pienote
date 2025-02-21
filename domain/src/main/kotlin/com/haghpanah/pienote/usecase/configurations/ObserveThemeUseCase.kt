package com.haghpanah.pienote.usecase.configurations

import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.repository.ConfigurationsRepository
import com.haghpanah.pienote.utils.PienotePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveThemeUseCase(
    private val repository: ConfigurationsRepository,
) {
    operator fun invoke(): Flow<ThemeType> = repository
        .observeCurrentTheme()
        .map { themeType -> themeType ?: ThemeType.SystemDefault }
}
