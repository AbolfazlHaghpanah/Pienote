package com.haghpanh.pienote.note.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.note.utils.FocusRequestType

@Immutable
data class NoteViewState(
    val note: Note = Note(),
    val category: Category? = null,
    val isEditing: Boolean = false,
    val categories: List<Category> = emptyList(),
    val focusRequestType: FocusRequestType = FocusRequestType.Non,
    val wantsToSelectPriority: Boolean = false
)
