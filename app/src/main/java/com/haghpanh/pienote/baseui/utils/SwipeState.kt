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
import kotlin.math.log

class SwipeState(
    val threshold: Float,
    val directionalSwipe: Swipe
) {
    var currentOffsetX by mutableFloatStateOf(0f)

    sealed interface Swipe {
        var isSwiped: Boolean
        var isOffsetAchieveThreshold: Boolean

        fun reset()

        data object LeftToRight : Swipe {
            override var isSwiped: Boolean by mutableStateOf(false)
            override var isOffsetAchieveThreshold: Boolean by mutableStateOf(false)
            override fun reset() {
                isSwiped = false
                isOffsetAchieveThreshold = false
            }
        }

        data object RightToLeft : Swipe {
            override var isSwiped: Boolean by mutableStateOf(false)
            override var isOffsetAchieveThreshold: Boolean by mutableStateOf(false)
            override fun reset() {
                isSwiped = false
                isOffsetAchieveThreshold = false

            }
        }

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
    }

    fun reset() {
        directionalSwipe.reset()
        currentOffsetX = 0f
    }
}

fun Modifier.swipeHandler(
    state: SwipeState,
    onSwipeRight: (() -> Unit)? = null,
    onSwipeLeft: (() -> Unit)? = null,
    shouldVibrateOnAchieveThreshold: Boolean = false
): Modifier = composed {

    when (state.directionalSwipe) {
        is SwipeState.Swipe.LeftToRight -> {
            val context = LocalContext.current

            LaunchedEffect(state.directionalSwipe.isOffsetAchieveThreshold && shouldVibrateOnAchieveThreshold) {
                if (state.directionalSwipe.isOffsetAchieveThreshold) {
                    (context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?)?.apply {
                        vibrate(100)
                    }
                }
            }

            this.pointerInput(null) {
                detectHorizontalDragGestures(
                    onDragStart = {
                        state.directionalSwipe.isSwiped = it.x > 0
                    },
                    onHorizontalDrag = { _, offset ->
                        state.currentOffsetX += offset

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
