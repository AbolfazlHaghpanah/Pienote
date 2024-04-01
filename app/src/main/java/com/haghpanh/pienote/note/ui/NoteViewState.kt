package com.haghpanh.pienote.note.ui

import androidx.compose.runtime.Immutable

@Immutable
data class NoteViewState(
    val note: Note? = null,
    val category: Category? = null,
    val isEditing : Boolean = false,
    val categories : List<Category> = emptyList()
)
