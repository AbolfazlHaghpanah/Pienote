package com.haghpanh.pienote.common_ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.haghpanh.pienote.R

data class Icon(
    val pienoteIcon : Int = R.mipmap.pienote_icon,
    val highPriority: Int = R.drawable.high_priority,
    val lowPriority: Int = R.drawable.low_priority,
    val mediumDensity : Int = R.drawable.density_medium
)

val LocalIcons = staticCompositionLocalOf { Icon() }