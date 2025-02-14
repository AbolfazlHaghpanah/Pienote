package com.haghpanah.pienote.utils

import java.util.prefs.Preferences

internal class PienotePreferencesImpl : PienotePreferences {
    private val prefs = Preferences.userRoot().node(DATA_STORE_NAME)

    companion object {
        const val DATA_STORE_NAME = "pienote-preferences"
    }
}