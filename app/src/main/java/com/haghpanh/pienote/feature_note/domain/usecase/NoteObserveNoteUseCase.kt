package com.haghpanh.pienote.feature_note.domain.usecase

import com.haghpanh.pienote.feature_note.domain.model.NoteWithCategoryDomainModel
import com.haghpanh.pienote.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteObserveNoteInfoUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(id: Int): Flow<NoteWithCategoryDomainModel> =
        noteRepository.observeNote(id)
}