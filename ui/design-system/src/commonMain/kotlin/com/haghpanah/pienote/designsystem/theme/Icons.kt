package com.haghpanah.pienote.designsystem.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class PienoteIcons(
    val pienoteIcon: Int = 1 //TODO
)

val LocalIcons = staticCompositionLocalOf { PienoteIcons() }
