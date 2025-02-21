package com.haghpanah.pienote.repository

import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import kotlinx.coroutines.flow.Flow

interface ConfigurationsRepository {
    fun observeCurrentTheme(): Flow<ThemeType?>
    suspend fun setTheme(themeType: ThemeType)
    fun observeAppLanguage(): Flow<SupportedLanguage>
    suspend fun setAppLanguage(language: SupportedLanguage)
}