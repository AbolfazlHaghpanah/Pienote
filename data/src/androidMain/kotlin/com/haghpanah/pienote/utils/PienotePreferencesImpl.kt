package com.haghpanah.pienote.utils

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

internal class PienotePreferencesImpl(context: Context) : PienotePreferences {
    private val Context.datastore by preferencesDataStore(DATA_STORE_NAME)
    private val dataStore = context.datastore

    companion object {
        const val DATA_STORE_NAME = "pienote-preferences"
    }
}