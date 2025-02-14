package com.haghpanah.pienote.shortcuthandler

import androidx.compose.runtime.mutableStateListOf

object KeyboardShortcutsManager {
    private val handlers = mutableStateListOf<Pair<ShortcutEvent, () -> Boolean>>()

    internal fun pushShortcutAction(
        key: ShortcutEvent,
        action: () -> Boolean
    ) {
        handlers.add(Pair(key, action))
    }

    internal fun popShortcutAction() {
        handlers.removeLast()
    }

    internal fun ShortcutEvent.handleKeyEvent(): Boolean {
        return handlers.lastOrNull {
            it.first == this
        }?.second?.invoke() ?: false
    }
}
