package com.haghpanah.pienote.core.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.haghpanah.pienote.core.component.DrawerState.DrawerValue
import com.haghpanah.pienote.core.component.DrawerState.DrawerValue.Close
import com.haghpanah.pienote.core.component.DrawerState.DrawerValue.Open
import kotlin.properties.Delegates

@Composable
fun PienoteDrawer(
    state: DrawerState,
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Row(modifier.fillMaxSize()) {
        AnimatedVisibility(state.isOpen) {
            drawerContent()
        }

        Box { content() }
    }
}

class DrawerState(
    initialValue: DrawerValue
) {
    private var value by Delegates.observable(initialValue) { _, _, newValue ->
        isOpen = newValue is Open
        isClosed = newValue is Close
    }

    var isOpen by mutableStateOf(initialValue is Open)
        private set

    var isClosed by mutableStateOf(initialValue is Close)
        private set

    fun close() {
        value = Close
    }

    fun open() {
        value = Open
    }

    sealed interface DrawerValue {
        data object Open : DrawerValue
        data object Close : DrawerValue
    }

    companion object {
        internal val saver = Saver<DrawerState, Boolean>(
            save = { state ->
                state.isOpen
            },
            restore = { isOpen ->
                DrawerState(if (isOpen) Open else Close)
            }
        )
    }
}

@Composable
fun rememberDrawerState(initialValue: DrawerValue): DrawerState {
    return rememberSaveable(DrawerState.saver) {
        DrawerState(initialValue)
    }
}

