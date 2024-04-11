package com.haghpanh.pienote.baseui.utils

import android.content.Context
import android.os.Vibrator
import android.util.Log
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext

class SwipeHandlerState(
    val threshold: Float
) {
    var offsetFromBegin by mutableFloatStateOf(0f)
    val rightSwipe = RightSwipe()
    val leftSwipe = LeftSwipe()

    interface Swipe {
        var isSwiped: Boolean
        var isOffsetAchieveThreshold: Boolean
    }

    class RightSwipe : Swipe {
        override var isSwiped by mutableStateOf(false)
        override var isOffsetAchieveThreshold by mutableStateOf(false)
    }

    class LeftSwipe : Swipe {
        override var isSwiped by mutableStateOf(false)
        override var isOffsetAchieveThreshold by mutableStateOf(false)
    }

    fun isSwiped() =
        rightSwipe.isSwiped || leftSwipe.isSwiped

    fun isOffsetAchieveThreshold() =
        leftSwipe.isOffsetAchieveThreshold || rightSwipe.isOffsetAchieveThreshold

    fun reset() {
        Log.d("mmd", "reset: ")
        this.leftSwipe.isSwiped = false
        this.rightSwipe.isSwiped = false
        this.leftSwipe.isOffsetAchieveThreshold = false
        this.rightSwipe.isOffsetAchieveThreshold = false
        this.offsetFromBegin = 0f
    }
}

fun Modifier.swipeHandler(
    state: SwipeHandlerState,
    onSwipeRight: (() -> Unit)? = null,
    onSwipeLeft: (() -> Unit)? = null,
    shouldVibrateOnAchieveThreshold: Boolean = false
): Modifier = composed {
    val context = LocalContext.current

    if (shouldVibrateOnAchieveThreshold) {
        if (onSwipeRight != null) {
            LaunchedEffect(state.rightSwipe.isOffsetAchieveThreshold) {
                if (state.rightSwipe.isOffsetAchieveThreshold) {
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.apply {
                        vibrate(100)
                    }
                }
            }
        }

        if (onSwipeLeft != null) {
            LaunchedEffect(state.leftSwipe.isOffsetAchieveThreshold) {
                if (state.leftSwipe.isOffsetAchieveThreshold) {
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.apply {
                        vibrate(100)
                    }
                }
            }
        }
    }

    this.pointerInput(null) {
        detectHorizontalDragGestures(
            onDragStart = { offset ->
                if (offset.x > 0) state.rightSwipe.isSwiped = true
                else if (offset.x < 0) state.leftSwipe.isSwiped = true
            },
            onHorizontalDrag = { _, offset ->
                state.offsetFromBegin += offset
                state.rightSwipe.isOffsetAchieveThreshold = state.offsetFromBegin > state.threshold
                state.leftSwipe.isOffsetAchieveThreshold = state.offsetFromBegin > -state.threshold
            },
            onDragEnd = {
                if (state.rightSwipe.isOffsetAchieveThreshold && onSwipeRight != null) {
                    onSwipeRight.invoke()
                } else if (state.leftSwipe.isOffsetAchieveThreshold && onSwipeLeft != null) {
                    onSwipeLeft.invoke()
                } else {
                    state.reset()
                }
            }
        )
    }
}

@Composable
fun rememberSwipeHandlerState(threshold: Float): SwipeHandlerState = remember {
    SwipeHandlerState(threshold)
}
