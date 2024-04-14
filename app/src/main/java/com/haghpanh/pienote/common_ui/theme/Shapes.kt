package com.haghpanh.pienote.common_ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

@Immutable
data class Shapes(
    val verySmall: CornerBasedShape = RoundedCornerShape(4.dp),
    val small: CornerBasedShape = RoundedCornerShape(8.dp),
    val medium: CornerBasedShape = RoundedCornerShape(12.dp),
    val large: CornerBasedShape = RoundedCornerShape(16.dp),
    val veryLarge: CornerBasedShape = RoundedCornerShape(24.dp),
    val huge: CornerBasedShape = RoundedCornerShape(30.dp),
    val rounded: CornerBasedShape = RoundedCornerShape(50)
) {
    @Composable
    fun toShapes(): Shapes = Shapes(
        small = small,
        medium = medium,
        large = large
    )
}

val LocalShapes = staticCompositionLocalOf {
    com.haghpanh.pienote.common_ui.theme.Shapes()
}