package com.haghpanah.pienote.utils

import com.haghpanah.pienote.model.ThemeType
import kotlinx.coroutines.flow.Flow

interface PienotePreferences {
    val observeTheme: Flow<ThemeType?>

    suspend fun setTheme(themeType: ThemeType)
}
