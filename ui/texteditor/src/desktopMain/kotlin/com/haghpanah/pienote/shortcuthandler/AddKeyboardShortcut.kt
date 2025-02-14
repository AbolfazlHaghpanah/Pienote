package com.haghpanah.pienote.shortcuthandler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.input.key.Key
import com.haghpanah.pienote.shortcuthandler.KeyboardShortcutsManager.popShortcutAction
import com.haghpanah.pienote.shortcuthandler.KeyboardShortcutsManager.pushShortcutAction

@Composable
fun addKeyboardShortcut(
    key: Key,
    action: () -> Boolean
) {
    DisposableEffect(Unit) {
        pushShortcutAction(ShortcutEvent(key, ShortcutType.SingleKey), action)
        onDispose {
            popShortcutAction()
        }
    }
}

@Composable
fun addKeyboardShortcut(
    event: ShortcutEvent,
    action: () -> Boolean
) {
    DisposableEffect(Unit) {
        pushShortcutAction(event, action)
        onDispose {
            popShortcutAction()
        }
    }
}