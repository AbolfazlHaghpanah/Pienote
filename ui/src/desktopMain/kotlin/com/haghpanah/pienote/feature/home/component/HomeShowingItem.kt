package com.haghpanah.pienote.feature.home.component

import androidx.compose.runtime.Immutable

@Immutable
data class HomeShowingItem(
    val isNote: Boolean,
    val id: Long
) {
    fun isEqualToCategory(id: Long): Boolean =
        !isNote && this.id == id

    fun isEqualToNote(id: Long): Boolean =
        isNote && this.id == id
}