package com.haghpanah.pienote.feature.note

import androidx.compose.runtime.Immutable
import com.haghpanah.pienote.model.CategoryDomainModel
import com.haghpanah.pienote.model.NoteDomainModel
import com.haghpanah.pienote.model.emptyNote
import com.haghpanh.pienote.commonui.utils.annotation.EffectState

@Immutable
data class NoteViewState(
    val note: NoteDomainModel = emptyNote(),
    val category: CategoryDomainModel? = null,
    val isEditing: Boolean = false,
    val categories: List<CategoryDomainModel> = emptyList(),
    val noteId: Int?,
    val isExist: Boolean,
    @EffectState
    val canNavigateBack: Boolean? = null,
) {
    val isEmptyNote = note.title.isEmpty() && note.markdown.isEmpty()
}

