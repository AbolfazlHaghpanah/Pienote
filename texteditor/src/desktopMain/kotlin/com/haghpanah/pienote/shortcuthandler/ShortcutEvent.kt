package com.haghpanah.pienote.shortcuthandler

import androidx.compose.ui.input.key.Key

data class ShortcutEvent(
    val key: Key,
    val type: ShortcutType = ShortcutType.SingleKey
)