package com.haghpanah.pienote.usecase.common

import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.utils.PienotePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ObserveThemeUseCase(
    private val preferences: PienotePreferences,
) {
    operator fun invoke(): Flow<ThemeType> = preferences
        .observeTheme
        .map { themeType -> themeType ?: ThemeType.SystemDefault }
}
