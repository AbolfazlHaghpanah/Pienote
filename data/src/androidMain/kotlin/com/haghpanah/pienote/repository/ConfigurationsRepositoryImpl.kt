package com.haghpanah.pienote.repository

import android.app.LocaleConfig
import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.haghpanah.pienote.model.SupportedLanguage
import com.haghpanah.pienote.model.ThemeType
import com.haghpanah.pienote.model.getDefaultLanguage
import com.haghpanah.pienote.utils.PienotePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class ConfigurationsRepositoryImpl(
    private val context: Context,
    private val preferences: PienotePreferences,
) : ConfigurationsRepository {

    @RequiresApi(33)
    private val localManager = context.getSystemService(LocaleManager::class.java)
    private val _currentLanguage = MutableStateFlow(getCurrentLanguage())

    init {
        if (Build.VERSION.SDK_INT >= 34) {
            localManager.overrideLocaleConfig = LocaleConfig(
                LocaleList.forLanguageTags(
                    SupportedLanguage.entries.joinToString(separator = ",") { it.tag }
                )
            )
        }
    }

    override fun observeCurrentTheme(): Flow<ThemeType?> =
        preferences.observeTheme

    override suspend fun setTheme(themeType: ThemeType) {
        preferences.setTheme(themeType)
    }

    override fun observeAppLanguage(): Flow<SupportedLanguage> {
        return _currentLanguage.asStateFlow()
    }

    override suspend fun setAppLanguage(language: SupportedLanguage) {
        val appLocale = LocaleListCompat.forLanguageTags(language.tag)
        AppCompatDelegate.setApplicationLocales(appLocale)
        _currentLanguage.value = getCurrentLanguage()
    }

    private fun getCurrentLanguage(): SupportedLanguage {
        val currentLocal = if (Build.VERSION.SDK_INT >= 33) {
            localManager.applicationLocales.get(0)
        } else {
            context.resources.configuration.locales[0]
        }

        return SupportedLanguage.entries.firstOrNull {
            currentLocal
                ?.toLanguageTag()
                ?.contains(it.tag)
                ?: false
        } ?: getDefaultLanguage()
    }
}
