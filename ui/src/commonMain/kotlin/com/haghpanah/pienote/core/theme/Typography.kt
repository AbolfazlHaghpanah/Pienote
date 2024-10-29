package com.haghpanah.pienote.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import org.jetbrains.compose.resources.Font
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.roboto_bold
import pienote.ui.generated.resources.roboto_regular

private val robotoBoldFont
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.roboto_bold,
            weight = FontWeight.Normal
        )
    )
private val robotoRegularFont
    @Composable
    get() = FontFamily(
        Font(
            resource = Res.font.roboto_regular,
            weight = FontWeight.Normal
        )
    )
private val baseline = Typography()


val pienoteTypography
    @Composable
    get() = Typography(
        displayLarge = baseline.displayLarge.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        displayMedium = baseline.displayMedium.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        displaySmall = baseline.displaySmall.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        headlineLarge = baseline.headlineLarge.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        headlineMedium = baseline.headlineMedium.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        headlineSmall = baseline.headlineSmall.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        titleLarge = baseline.titleLarge.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        titleMedium = baseline.titleMedium.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        titleSmall = baseline.titleSmall.copy(
            fontFamily = robotoBoldFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        bodyLarge = baseline.bodyLarge.copy(
            fontFamily = robotoRegularFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        bodyMedium = baseline.bodyMedium.copy(
            fontFamily = robotoRegularFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        bodySmall = baseline.bodySmall.copy(
            fontFamily = robotoRegularFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        labelLarge = baseline.labelLarge.copy(
            fontFamily = robotoRegularFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        labelMedium = baseline.labelMedium.copy(
            fontFamily = robotoRegularFont,
            textDirection = TextDirection.ContentOrLtr
        ),
        labelSmall = baseline.labelSmall.copy(
            fontFamily = robotoRegularFont,
            textDirection = TextDirection.ContentOrLtr
        ),
    )
