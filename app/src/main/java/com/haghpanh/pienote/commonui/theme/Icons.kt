package com.haghpanh.pienote.commonui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.haghpanh.pienote.R

data class PienoteIcons(
    val pienoteIcon: Int = R.mipmap.pienote_icon,
    val highPriority: Int = R.drawable.high_priority,
    val lowPriority: Int = R.drawable.low_priority,
    val mediumDensity: Int = R.drawable.density_medium
)

val LocalIcons = staticCompositionLocalOf { PienoteIcons() }
