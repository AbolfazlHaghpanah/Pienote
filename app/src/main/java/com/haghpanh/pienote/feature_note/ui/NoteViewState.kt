package com.haghpanh.pienote.feature_note.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.feature_note.utils.FocusRequestType

@Immutable
data class NoteViewState(
    val note: Note = Note(),
    val category: Category? = null,
    val isEditing: Boolean = false,
    val categories: List<Category> = emptyList(),
    val focusRequestType: FocusRequestType = FocusRequestType.Non,
    val isExist: Boolean
) {
    val isEmptyNote = note.title.isNullOrEmpty() && note.note.isNullOrEmpty()
}
