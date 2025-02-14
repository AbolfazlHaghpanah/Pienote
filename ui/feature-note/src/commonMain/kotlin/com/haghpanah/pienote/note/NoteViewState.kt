package com.haghpanah.pienote.note

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.baseui.annotation.EffectState
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.model.emptyNote

@Immutable
data class NoteViewState(
    val note: NoteDomainModel = emptyNote(),
    val category: CategoryDomainModel? = null,
    val isEditing: Boolean = false,
    val categories: List<CategoryDomainModel> = emptyList(),
    val noteId: Long?,
    val isExist: Boolean,
    @EffectState
    val canNavigateBack: Boolean? = null,
) {
    val isEmptyNote = note.title.isEmpty() && note.markdown.isEmpty()
}

