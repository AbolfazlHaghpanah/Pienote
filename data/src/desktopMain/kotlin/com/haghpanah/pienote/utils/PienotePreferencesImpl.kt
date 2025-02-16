package com.haghpanah.pienote.utils

import com.haghpanah.pienote.model.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.prefs.Preferences

internal class PienotePreferencesImpl : PienotePreferences {
    private val prefs = Preferences.userRoot().node(DATA_STORE_NAME)

    private val themeFlow by lazy {
        val themePref = Json.decodeFromString<ThemeType?>(
            string = prefs.get(THEME_PREF_KEY, null)
        )

        MutableStateFlow(themePref)
    }

    init {
        prefs.addPreferenceChangeListener { event ->
            when (event.key) {
                THEME_PREF_KEY -> themeFlow.value = Json.decodeFromString<ThemeType?>(
                    string = prefs.get(THEME_PREF_KEY, null)
                )
            }
        }
    }

    override val observeTheme: Flow<ThemeType?> = themeFlow

    override suspend fun setTheme(themeType: ThemeType) {
        val themeJson = Json.encodeToString<ThemeType>(themeType)
        prefs.put(THEME_PREF_KEY, themeJson)
    }


    companion object {
        const val DATA_STORE_NAME = "pienote-preferences"
        const val THEME_PREF_KEY = "theme"
    }
}