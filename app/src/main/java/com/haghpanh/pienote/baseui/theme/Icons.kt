package com.haghpanh.pienote.baseui.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class Icon(
    val a : Int = 0
)

val LocalIcons = staticCompositionLocalOf { Icon() }