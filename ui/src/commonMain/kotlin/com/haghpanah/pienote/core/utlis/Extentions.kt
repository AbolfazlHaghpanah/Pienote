package com.haghpanah.pienote.core.utlis

import androidx.compose.ui.graphics.Color

fun <T> List<T>.chunkedEven(): List<List<T>> {
    if (this.isEmpty()) return emptyList()
    val firstChunkSize = if (this.size % 2 == 0) 2 else 1
    val firstChunk = this.take(firstChunkSize)
    val remainingChunks = this.drop(firstChunkSize).chunked(2)
    return listOf(firstChunk) + remainingChunks
}

fun String.toComposeColor(): Color {
    val color = this.trimStart('#')
    return when (color.length) {
        6 -> Color(
            red = Integer.parseInt(color.substring(0, 2), 16) / 255f,
            green = Integer.parseInt(color.substring(2, 4), 16) / 255f,
            blue = Integer.parseInt(color.substring(4, 6), 16) / 255f,
            alpha = 1f
        )
        8 -> Color(
            red = Integer.parseInt(color.substring(2, 4), 16) / 255f,
            green = Integer.parseInt(color.substring(4, 6), 16) / 255f,
            blue = Integer.parseInt(color.substring(6, 8), 16) / 255f,
            alpha = Integer.parseInt(color.substring(0, 2), 16) / 255f
        )
        else -> throw IllegalArgumentException("Unknown color format. Use #RRGGBB or #AARRGGBB.")
    }
}
