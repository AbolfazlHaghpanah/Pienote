package com.haghpanh.pienote.home.ui

import androidx.compose.runtime.Immutable

@Immutable
data class HomeViewState(
    val notes : List<Note>?,
    val quickNoteTitle : String?,
    val quickNoteNote : String?
)

