package com.haghpanh.pienote.baseui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.haghpanh.pienote.R

val robotoBoldFont = FontFamily(Font(R.font.roboto_bold, FontWeight.Normal))
val robotoLightFont = FontFamily(Font(R.font.roboto_light, FontWeight.Normal))
val robotoMediumFont = FontFamily(Font(R.font.roboto_medium, FontWeight.Normal))
val robotoThinFont = FontFamily(Font(R.font.roboto_thin, FontWeight.Normal))
val robotoRegularFont = FontFamily(Font(R.font.roboto_regular, FontWeight.Normal))

@Immutable
data class Types(
    val h1: TextStyle = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    ),

    val h2: TextStyle = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),

    val h6: TextStyle = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),

    val subtitle1: TextStyle = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    val subtitle2: TextStyle = TextStyle(
        fontFamily = robotoMediumFont,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),

    val body1: TextStyle = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),

    val body2: TextStyle = TextStyle(
        fontFamily = robotoMediumFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),

    val button: TextStyle = TextStyle(
        fontFamily = robotoBoldFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),

    val caption: TextStyle = TextStyle(
        fontFamily = robotoLightFont,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),

    val overLine: TextStyle = TextStyle(
        fontFamily = robotoMediumFont,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
) {
    @Composable
    fun toTypography(): Typography = Typography(
        defaultFontFamily = robotoBoldFont,
        h6 = h6,
        subtitle1 = subtitle1,
        subtitle2 = subtitle2,
        body1 = body1,
        body2 = body2,
        button = button,
        caption = caption,
        overline = overLine
    )
}

val LocalTypography = staticCompositionLocalOf { Types() }
