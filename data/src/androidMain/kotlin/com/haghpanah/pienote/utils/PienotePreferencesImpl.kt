package com.haghpanah.pienote.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.haghpanah.pienote.model.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class PienotePreferencesImpl(context: Context) : PienotePreferences {
    private val Context.datastore by preferencesDataStore(DATA_STORE_NAME)
    private val dataStore = context.datastore

    override val observeTheme: Flow<ThemeType?> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(THEME_KEY)]
                ?.let { Json.decodeFromString<ThemeType>(it) }
        }

    override suspend fun setTheme(themeType: ThemeType) {
        dataStore.edit {
            it[stringPreferencesKey(THEME_KEY)] = Json.encodeToString(themeType)
        }
    }

    companion object {
        const val DATA_STORE_NAME = "pienote-preferences"
        const val THEME_KEY = "theme-key-data-store"
    }
}