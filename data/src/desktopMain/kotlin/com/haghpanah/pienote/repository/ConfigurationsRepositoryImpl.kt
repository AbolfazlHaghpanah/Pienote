package com.haghpanah.pienote.repository

import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.utils.PienotePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class ConfigurationsRepositoryImpl(
    private val preferences: PienotePreferences,
) : ConfigurationsRepository {
    override fun observeCurrentTheme(): Flow<ThemeType?> =
        preferences.observeTheme

    override suspend fun setTheme(themeType: ThemeType) {
        preferences.setTheme(themeType)
    }

    override fun observeAppLanguage(): Flow<SupportedLanguage> {
        //TODO
        return flowOf()
    }

    override suspend fun setAppLanguage(language: SupportedLanguage) {
        //TODO
    }
}
