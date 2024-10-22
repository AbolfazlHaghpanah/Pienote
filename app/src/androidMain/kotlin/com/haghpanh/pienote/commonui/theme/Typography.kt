package pienote.commonui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import com.haghpanh.pienote.R

private val robotoBoldFont = FontFamily(Font(R.font.roboto_bold, FontWeight.Normal))
private val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))
private val baseline = Typography()

private val pienoteTypography = Typography(
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

val LocalTypography = staticCompositionLocalOf { pienoteTypography }
