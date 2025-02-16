package com.haghpanah.pienote.usecase.common

import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.utils.PienotePreferences

class SetThemeUseCase(
    private val preferences: PienotePreferences,
) {
    suspend operator fun invoke(themeType: ThemeType) {
        preferences.setTheme(themeType)
    }
}
