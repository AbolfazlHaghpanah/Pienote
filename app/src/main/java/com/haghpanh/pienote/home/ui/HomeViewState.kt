package com.haghpanh.pienote.home.ui

import androidx.compose.runtime.Immutable

@Immutable
data class HomeViewState(
    val notes : List<Note>? = null,
    val quickNoteTitle : String? = null,
    val quickNoteNote : String? = null,
    val hasClickedOnQuickNote : Boolean = false
)

