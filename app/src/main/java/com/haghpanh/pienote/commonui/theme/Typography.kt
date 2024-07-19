package com.haghpanh.pienote.commonui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.haghpanh.pienote.R

private val robotoBoldFont = FontFamily(Font(R.font.roboto_bold, FontWeight.Normal))
private val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))
private val baseline = Typography()

private val pienoteTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = robotoBoldFont),
    displayMedium = baseline.displayMedium.copy(fontFamily = robotoBoldFont),
    displaySmall = baseline.displaySmall.copy(fontFamily = robotoBoldFont),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = robotoBoldFont),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = robotoBoldFont),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = robotoBoldFont),
    titleLarge = baseline.titleLarge.copy(fontFamily = robotoBoldFont),
    titleMedium = baseline.titleMedium.copy(fontFamily = robotoBoldFont),
    titleSmall = baseline.titleSmall.copy(fontFamily = robotoBoldFont),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = robotoRegularFont),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = robotoRegularFont),
    bodySmall = baseline.bodySmall.copy(fontFamily = robotoRegularFont),
    labelLarge = baseline.labelLarge.copy(fontFamily = robotoRegularFont),
    labelMedium = baseline.labelMedium.copy(fontFamily = robotoRegularFont),
    labelSmall = baseline.labelSmall.copy(fontFamily = robotoRegularFont),
)

val LocalTypography = staticCompositionLocalOf { pienoteTypography }
