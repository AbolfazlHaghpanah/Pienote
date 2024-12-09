package com.haghpanah.pienote.utils

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable

internal object TextEditorFontProvider {
    lateinit var typography: Typography
        private set

    @Composable
    fun Initialize(
        typography: Typography
    ) {
        this.typography = typography
    }
}

@Composable
fun InitiateTextEditorFont(
    typography: Typography
) {
    TextEditorFontProvider.Initialize(
        typography = typography
    )
}