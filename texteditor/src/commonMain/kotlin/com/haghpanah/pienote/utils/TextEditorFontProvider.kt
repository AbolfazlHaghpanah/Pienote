package com.haghpanah.pienote.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily

object TextEditorFontProvider {
    lateinit var robotoBoldFont: FontFamily
        private set
    lateinit var robotoRegularFont: FontFamily
        private set

    @Composable
    fun Initialize(
        regularFont: FontFamily,
        boldFont: FontFamily
    ) {
        robotoBoldFont = regularFont
        robotoRegularFont = boldFont
    }
}
