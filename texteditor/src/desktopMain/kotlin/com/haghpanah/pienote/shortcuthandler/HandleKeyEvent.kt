package com.haghpanah.pienote.shortcuthandler

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import com.haghpanah.pienote.shortcuthandler.KeyboardShortcutsManager.handleKeyEvent

fun handleKeyEvent(keyEvent: KeyEvent): Boolean {
    val shortcutEvent = if (keyEvent.type == KeyEventType.KeyUp) {
        when {
            keyEvent.isMetaPressed -> ShortcutEvent(keyEvent.key, ShortcutType.WithMeta)

            keyEvent.isShiftPressed -> ShortcutEvent(keyEvent.key, ShortcutType.WithShift)

            keyEvent.isAltPressed -> ShortcutEvent(keyEvent.key, ShortcutType.WithAlt)

            keyEvent.isCtrlPressed -> ShortcutEvent(keyEvent.key, ShortcutType.WithCtrl)

            else -> ShortcutEvent(keyEvent.key, ShortcutType.SingleKey)
        }
    } else {
        null
    }

    return shortcutEvent?.handleKeyEvent() ?: false
}