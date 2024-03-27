package com.haghpanh.pienote.baseui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.haghpanh.pienote.R

data class Icon(
    val pienoteIcon : Int = R.mipmap.pienote_icon
)

val LocalIcons = staticCompositionLocalOf { Icon() }