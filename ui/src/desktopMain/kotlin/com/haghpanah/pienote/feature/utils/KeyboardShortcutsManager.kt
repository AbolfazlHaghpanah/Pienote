package com.haghpanah.pienote.feature.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.input.key.Key

object KeyboardShortcutsManager {
    private val handlers = mutableStateListOf<Pair<Key, () -> Boolean>>()

    private fun pushShortcutAction(
        key: Key,
        action: () -> Boolean
    ) {
        handlers.add(Pair(key, action))
    }

    private fun popShortcutAction() {
        handlers.removeLast()
    }

    fun Key.handleKeyEvent(): Boolean {
        return handlers.lastOrNull {
            it.first == this
        }?.second?.invoke() ?: false
    }

    @Composable
    fun addKeyboardShortcut(
        key: Key,
        action: () -> Boolean
    ) {
        DisposableEffect(Unit) {
            pushShortcutAction(key, action)
            onDispose {
                popShortcutAction()
            }
        }
    }
}
