package com.haghpanh.pienote.features.note.ui

import androidx.compose.runtime.Immutable
import com.haghpanh.pienote.features.note.utils.FocusRequestType

@Immutable
data class NoteViewState(
    val note: Note = Note(),
    val category: Category? = null,
    val isEditing: Boolean = false,
    val categories: List<Category> = emptyList(),
    val focusRequestType: FocusRequestType = FocusRequestType.Title,
    val noteId: Int?,
    val isExist: Boolean
) {
    val isEmptyNote = note.title.isNullOrEmpty() && note.note.isNullOrEmpty()
}
