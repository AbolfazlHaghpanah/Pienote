package pienote.features.note.ui

import androidx.compose.runtime.Immutable
import pienote.commonui.utils.annotation.EffectState

@Immutable
data class NoteViewState(
    val note: Note = Note(),
    val category: Category? = null,
    val isEditing: Boolean = false,
    val categories: List<Category> = emptyList(),
    val noteId: Int?,
    val isExist: Boolean,
    @EffectState
    val canNavigateBack: Boolean? = null,
) {
    val isEmptyNote = note.title.isNullOrEmpty() && note.note.isNullOrEmpty()
}
