package com.haghpanh.pienote.note.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import kotlin.math.roundToInt

class NoteNestedScrollConnection(
    private val initialAlpha: Float = 1f,
    private val initialScale: Float = 1f
) : NestedScrollConnection {
    private var consumedFromStart = 0

    var imageOffset by mutableIntStateOf(0)
    var imageScale by mutableFloatStateOf(initialScale)
        private set
    var imageAlpha by mutableFloatStateOf(initialAlpha)
        private set

    fun reset() {
        imageOffset = 0
        imageAlpha = initialAlpha
        imageScale = initialScale
        consumedFromStart = 0
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        consumedFromStart += consumed.y.roundToInt()
        val delta = consumed.y / 800

        imageAlpha += delta
        imageScale -= delta / 10
        imageOffset = -consumedFromStart

        return super.onPostScroll(consumed, available, source)
    }
}

@Composable
fun rememberNoteNestedScrollConnection(
    initialAlpha: Float = 1f,
    initialScale: Float = 1f,
): NoteNestedScrollConnection = remember {
    NoteNestedScrollConnection(
        initialAlpha = initialAlpha,
        initialScale = initialScale
    )
}