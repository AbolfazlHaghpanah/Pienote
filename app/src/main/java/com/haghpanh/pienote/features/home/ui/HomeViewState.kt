package com.haghpanh.pienote.features.home.ui

import androidx.compose.runtime.Immutable

@Immutable
data class HomeViewState(
    val notes : List<Note>? = null,
    val categories : List<Category>? = null,
    val quickNoteTitle : String? = null,
    val quickNoteNote : String? = null,
    val hasClickedOnQuickNote : Boolean = false
)

