package com.haghpanh.pienote.commonui.utils

import android.content.Context
import android.os.Vibrator
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


/**
 * Class representing the state of a swipe gesture.
 *
 * @param threshold The threshold at which the swipe is considered to be completed.
 * @param directionalSwipe The type of swipe (left to right, right to left, or both).
 */
class SwipeState(
    val threshold: Float,
    val directionalSwipe: Swipe
) {
    var currentOffsetX by mutableFloatStateOf(0f)

    /**
     * Interface defining properties and methods for a swipe gesture.
     */
    interface Swipe {
        var isSwiped: Boolean
        var isOffsetAchieveThreshold: Boolean

        fun reset()
    }

    /**
     * Represents a left-to-right swipe.
     */
    class LeftToRight : Swipe {
        override var isSwiped: Boolean by mutableStateOf(false)
        override var isOffsetAchieveThreshold: Boolean by mutableStateOf(false)
        override fun reset() {
            isSwiped = false
            isOffsetAchieveThreshold = false
        }
    }

    //TODO implement action
    /**
     * Represents a right-to-left swipe.
     */
    class RightToLeft : Swipe {
        override var isSwiped: Boolean by mutableStateOf(false)
        override var isOffsetAchieveThreshold: Boolean by mutableStateOf(false)
        override fun reset() {
            isSwiped = false
            isOffsetAchieveThreshold = false
        }
    }

    //TODO implement actions
    /**
     * Represents a swipe gesture in both directions.
     */
    class Both : Swipe {
        var isSwipedRight by mutableStateOf(false)
        var isSwipedLeft by mutableStateOf(false)
        var isRightOffsetAchieveThreshold by mutableStateOf(false)
        var isLeftOffsetAchieveThreshold by mutableStateOf(false)

        override var isSwiped: Boolean = isSwipedLeft || isSwipedRight
        override var isOffsetAchieveThreshold: Boolean =
            isLeftOffsetAchieveThreshold || isRightOffsetAchieveThreshold

        override fun reset() {
            isSwipedRight = false
            isSwipedLeft = false
            isRightOffsetAchieveThreshold = false
            isLeftOffsetAchieveThreshold = false
            isSwiped = false
            isOffsetAchieveThreshold = false
        }
    }

    fun reset() {
        directionalSwipe.reset()
        currentOffsetX = 0f
    }
}

/**
 * Adds swipe gesture detection to a Composable.
 *
 * @param state The SwipeState instance managing the swipe state.
 * @param onSwipeRight A callback to be invoked when a right swipe is detected.
 * @param onSwipeLeft A callback to be invoked when a left swipe is detected.
 * @param shouldVibrateOnAchieveThreshold Whether to vibrate when the threshold is achieved.
 */
fun Modifier.swipeHandler(
    state: SwipeState,
    onSwipeRight: (() -> Unit)? = null,
    onSwipeLeft: (() -> Unit)? = null,
    shouldVibrateOnAchieveThreshold: Boolean = false
): Modifier = composed {

    when (state.directionalSwipe) {
        is SwipeState.LeftToRight -> {
            val context = LocalContext.current

            //TODO VIBRATOR_SERVICE is deprecated
            LaunchedEffect(state.directionalSwipe.isOffsetAchieveThreshold && shouldVibrateOnAchieveThreshold) {
                if (state.directionalSwipe.isOffsetAchieveThreshold) {
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.apply {
                        vibrate(100)
                    }
                }
            }

            this.pointerInput(null) {
                detectHorizontalDragGestures(
                    onHorizontalDrag = { _, offset ->
                        state.currentOffsetX += offset
                        state.directionalSwipe.isSwiped = state.currentOffsetX > 0
                        state.directionalSwipe.isOffsetAchieveThreshold =
                            state.currentOffsetX > state.threshold
                    },
                    onDragEnd = {
                        if (state.directionalSwipe.isOffsetAchieveThreshold && onSwipeRight != null) {
                            onSwipeRight.invoke()
                        }

                        state.reset()
                    }
                )
            }
        }

        else -> {
            this
        }
    }
}

/**
 * Remembers the swipe state.
 *
 * @param threshold The threshold for the swipe gesture.
 * @param swipeType The type of swipe gesture.
 * @return The SwipeState instance.
 */
@Composable
fun rememberSwipeState(
    threshold: Float,
    swipeType: SwipeState.Swipe
): SwipeState = remember {
    SwipeState(
        threshold = threshold,
        directionalSwipe = swipeType
    )
}
