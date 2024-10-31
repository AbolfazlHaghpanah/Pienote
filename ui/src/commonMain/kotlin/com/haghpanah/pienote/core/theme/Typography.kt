package com.haghpanah.pienote.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import com.haghpanah.pienote.core.theme.PienoteFontProvider.robotoBoldFont
import com.haghpanah.pienote.core.theme.PienoteFontProvider.robotoRegularFont
import org.jetbrains.compose.resources.Font
import pienote.ui.generated.resources.Res
import pienote.ui.generated.resources.roboto_bold
import pienote.ui.generated.resources.roboto_regular

internal object PienoteFontProvider {
    lateinit var robotoBoldFont: FontFamily
    lateinit var robotoRegularFont: FontFamily

    @Composable
    fun Initialize() {
        robotoBoldFont = FontFamily(
            Font(
                resource = Res.font.roboto_bold,
                weight = FontWeight.Normal
            )
        )
        robotoRegularFont = FontFamily(
            Font(
                resource = Res.font.roboto_regular,
                weight = FontWeight.Normal
            )
        )
    }
}

fun createPienoteTypography() = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    displayMedium = Typography().displayMedium.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    titleLarge = Typography().titleLarge.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = robotoBoldFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = robotoRegularFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = robotoRegularFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = robotoRegularFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = robotoRegularFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = robotoRegularFont,
        textDirection = TextDirection.ContentOrLtr
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = robotoRegularFont,
        textDirection = TextDirection.ContentOrLtr
    ),
)
